package com.weblearn.akka.chapter5;

import com.weblearn.akka.utils.ActorSystemManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

public class MasterRouterActor extends UntypedActor {

	ActorRef router = null;
	
	@Override
	public void preStart() throws Exception {
		router = getContext().actorOf(new RoundRobinPool(3).props(Props.create(TaskActor.class)), "taskActor");
		System.out.println("router : " + router);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		router.tell(msg, getSender());
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		ActorRef routerActor = system.actorOf(Props.create(MasterRouterActor.class), "masterRouterActor");
		routerActor.tell("helloA", ActorRef.noSender());
		routerActor.tell("helloB", ActorRef.noSender());
		routerActor.tell("helloC", ActorRef.noSender());
	}
}
