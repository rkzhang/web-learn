package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class CircuitBreakerActor extends UntypedActor {

	private ActorRef workerChild;
	
	private static SupervisorStrategy strategy = new OneForOneStrategy(20, Duration.create("1 minute"), 
		new Function<Throwable, SupervisorStrategy.Directive>() {
			@Override
			public Directive apply(Throwable arg0) throws Exception {
				
				return SupervisorStrategy.resume();
			}
		});
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		workerChild = getContext().actorOf(Props.create(CircuitBreakerWorkerActor.class), "workerActor");
		
	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		workerChild.tell(message, getSelf());
	}

}
