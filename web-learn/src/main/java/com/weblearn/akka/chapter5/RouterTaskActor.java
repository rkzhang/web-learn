package com.weblearn.akka.chapter5;

import java.util.ArrayList;
import java.util.List;

import com.weblearn.akka.utils.ActorSystemManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

public class RouterTaskActor extends UntypedActor {

	private Router router;
	
	@Override
	public void preStart() throws Exception {
		List<Routee> listRoutee = new ArrayList<Routee>();
		for(int i = 0; i < 3; i++) {
			ActorRef ref = getContext().actorOf(Props.create(RouteeActor.class), "routeeActor" + i);
			listRoutee.add(new ActorRefRoutee(ref));
		}
		router = new Router(new RoundRobinRoutingLogic(), listRoutee);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		router.route(msg, getSelf());
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		ActorRef routerActor = system.actorOf(Props.create(RouterTaskActor.class), "routerTaskActor");
		routerActor.tell("helloA", ActorRef.noSender());
		routerActor.tell("helloB", ActorRef.noSender());
		routerActor.tell("helloC", ActorRef.noSender());
	}
}
