package com.weblearn.akka.chapter2;

import java.util.concurrent.Callable;
import org.joda.time.DateTime;

import com.weblearn.akka.utils.ActorSystemManager;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.CircuitBreaker;
import scala.concurrent.duration.Duration;

public class CircuitBreakerWorkerActor extends UntypedActor{

	private CircuitBreaker breaker;
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		this.breaker = new CircuitBreaker(getContext().dispatcher(), getContext().system().scheduler(), 
				5, Duration.create(2, "s"), Duration.create(1, "min"));
		
		breaker.onOpen(() -> {
			DateTime dateTime = new DateTime();
			System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss") + " ---> Actor CircuitBreaker 开启");
		});
		
		breaker.onHalfOpen(() -> { 
			DateTime dateTime = new DateTime();
			System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss") + " ---> Actor CircuitBreaker 半开启");
		});
		
		breaker.onClose(() -> {
			DateTime dateTime = new DateTime();
			System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss") + " ---> Actor CircuitBreaker 关闭");
		});
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String && ((String) message).startsWith("sync")) {
			String msg = (String) message;
			getSender().tell(breaker.callWithSyncCircuitBreaker(new Callable<String>() {

				@Override
				public String call() throws Exception {
					System.out.println("msg: " + msg);
					Thread.sleep(3000);
					return msg;
				}
				
			}), getSelf());
		}	
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		ActorRef ar = system.actorOf(Props.create(CircuitBreakerActor.class), "watchActor");
		while(true) {
			ar.tell("sync", ActorRef.noSender());
		}
	}
}
