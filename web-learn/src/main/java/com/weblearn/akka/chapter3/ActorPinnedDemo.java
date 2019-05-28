package com.weblearn.akka.chapter3;

import com.weblearn.akka.utils.ActorSystemManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ActorPinnedDemo extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(getSelf() + " ---> " + msg + " - " + Thread.currentThread().getName());
		Thread.sleep(5000);
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		for(int i = 0 ; i < 20; i++) {
			ActorRef ref = system.actorOf(Props.create(ActorPinnedDemo.class).withDispatcher("my-pinned-dispatcher"), "actorDemo" + i);
			ref.tell("hello pinned", ActorRef.noSender());
		}
	}
}
