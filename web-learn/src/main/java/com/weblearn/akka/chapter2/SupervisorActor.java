package com.weblearn.akka.chapter2;

import java.io.IOException;
import java.sql.SQLException;

import akka.actor.ActorNotFound;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.SupervisorStrategy.Directive;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.actor.Terminated;
import akka.japi.Function;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class SupervisorActor extends UntypedActor {

	private SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create("1 minute"), 
			new Function<Throwable, SupervisorStrategy.Directive>() {		
		@Override
		public Directive apply(Throwable t) throws Exception {
			if(t instanceof IOException) {
				System.out.println("=============IOException============");
				return SupervisorStrategy.resume();	//恢复运行
				
			} else if(t instanceof IndexOutOfBoundsException) {
				System.out.println("=============IndexOutOfBoundsException===========");
				return SupervisorStrategy.restart();	//重启
				
			} else if(t instanceof SQLException) {
				System.out.println("=============SQLException===========");
				return SupervisorStrategy.stop();	//停止
				
			} else {
				System.out.println("=============escalate=============");
				return SupervisorStrategy.escalate();	//升级失败
			}
		}
	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
	
	@Override
	public void preStart() throws Exception {
		System.out.println("--------- preStart ----------");
		//创建子Actor
		ActorRef workerActor = getContext().actorOf(Props.create(WorkerActor.class), "workerActor");
		System.out.println(workerActor.toString());
		//监控生命周期
		getContext().watch(workerActor);
		
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof Terminated) {
			Terminated ter = (Terminated) msg;
			System.out.println(ter.getActor() + "已经终止");
		} else {
			System.out.println("stateCount=" + msg);
		}		
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef ar = system.actorOf(Props.create(SupervisorActor.class), "supervisorActor");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ActorSelection as = system.actorSelection("/user/supervisorActor/workerActor");
		Timeout timeout = new Timeout(Duration.create(3, "seconds"));
		Future<ActorRef> fu = as.resolveOne(timeout);
		
		fu.onSuccess(new OnSuccess<ActorRef>() {

			@Override
			public void onSuccess(ActorRef ref) throws Throwable {
				System.out.println("查找到Actor: " + ref);
			}
			
		}, system.dispatcher());
		
		fu.onFailure(new OnFailure() {
			
			@Override
			public void onFailure(Throwable ex) throws Throwable {
				if(ex instanceof ActorNotFound) {
					System.out.println("没找到Actor: " + ex.getMessage());
				}
				
			}
		}, system.dispatcher());
		
		while(!fu.isCompleted()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		ActorRef worker = fu.value().get().get();
		//worker.tell(new IOException(), ActorRef.noSender());
		//worker.tell(new SQLException("SQL异常"), ActorRef.noSender());
		worker.tell(new IndexOutOfBoundsException(), ActorRef.noSender());
		worker.tell("getValue", ActorRef.noSender());
	}
}
