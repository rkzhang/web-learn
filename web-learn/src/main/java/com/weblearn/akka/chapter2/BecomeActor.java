package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

public class BecomeActor extends UntypedActor {
	
	Procedure<Object> proce = new Procedure<Object>() {

		@Override
		public void apply(Object message) throws Exception {
			System.out.println("become : " + message);
		}
	};

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("接受消息: " + msg);
		getContext().become(proce);
		System.out.println("------------------------");
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef ref = system.actorOf(Props.create(BecomeActor.class), "becomeActor");
		ref.tell("hello", ActorRef.noSender());
		ref.tell("hi", ActorRef.noSender());
		ref.tell("hi", ActorRef.noSender());
		ref.tell("hi", ActorRef.noSender());
	}
}
