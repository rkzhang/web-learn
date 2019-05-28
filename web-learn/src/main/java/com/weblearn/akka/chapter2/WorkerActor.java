package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.Option;

public class WorkerActor extends UntypedActor {
	
	//状态数据
	private int stateCount = 1;
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		System.out.println("worker actor postRestart begin " + this.stateCount);
		super.postRestart(reason);
		System.out.println("worker actor postRestart end " + this.stateCount);
	}

	@Override
	public void preRestart(Throwable reason, Option<Object> message) throws Exception {
		System.out.println("worker actor preRestart begin " + this.stateCount);
		super.preRestart(reason, message);
		System.out.println("worker actor preRestart end " + this.stateCount);
	}

	@Override
	public void preStart() throws Exception {
		super.preStart();
		System.out.println("worker actor preStart");
	}

	@Override
	public void postStop() throws Exception {
		super.postStop();
		System.out.println("Worker postStop");
	}


	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("收到消息: " + msg);
		//模拟计算任务
		this.stateCount++;
		if(msg instanceof Exception) {
			throw (Exception) msg;
		} else if("getValue".equals(msg)) {
			//返回当前数据
			getSender().tell(stateCount, getSelf());
		} else {
			unhandled(msg);
		}
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef ar = system.actorOf(Props.create(WorkerActor.class), "workerActor");
		system.stop(ar);
		
		//ar.tell(PoisonPill.getInstance(), ActorRef.noSender());
		//ar.tell(Kill.getInstance(), ActorRef.noSender());
		
	}
}
