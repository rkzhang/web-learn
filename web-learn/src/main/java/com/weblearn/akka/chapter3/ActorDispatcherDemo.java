package com.weblearn.akka.chapter3;

import com.weblearn.akka.chapter2.ActorDemo;
import com.weblearn.akka.utils.ActorSystemManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorDispatcherDemo {

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		ActorRef ref = system.actorOf(Props.create(ActorDemo.class).withDispatcher("my-forkjoin-dispatcher"), "actorDemo");
	}
}
