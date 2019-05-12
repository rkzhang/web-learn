package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ActorDemo extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(msg.toString());
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef actorRef = system.actorOf(Props.create(ActorDemo.class), "actorDemo");
		actorRef.tell("Hello Akko", ActorRef.noSender());
	}
}
