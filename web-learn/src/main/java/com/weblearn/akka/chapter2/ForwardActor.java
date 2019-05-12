package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ForwardActor extends UntypedActor {
	
	private ActorRef target = getContext().actorOf(Props.create(TargetActor.class), "targetActor");

	@Override
	public void onReceive(Object msg) throws Exception {
		target.forward(msg, getContext());
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef actorRef = system.actorOf(Props.create(ForwardActor.class), "propsActor");
		actorRef.tell("Hello Akko - ForwardActor", ActorRef.noSender());
	}
}
