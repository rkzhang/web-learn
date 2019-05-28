package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

public class WatchActor extends UntypedActor {

	ActorRef child = null;
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof String && msg.equals("stopChild")) {
			getContext().stop(child);
		} else if (msg instanceof Terminated) {
			Terminated t = (Terminated) msg;
			System.out.println("监控到 " + t.getActor() + " 停止了");
		} else {
			unhandled(msg);
		}

	}

	@Override
	public void postStop() throws Exception {
		System.out.println("WatchActor postStop");
	}

	@Override
	public void preStart() throws Exception {
		//创建子级Actor
		child = this.getContext().actorOf(Props.create(WorkerActor.class), "workerActor");
		//监控child
		getContext().watch(child);
	}
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef ar = system.actorOf(Props.create(WatchActor.class), "watchActor");
		system.stop(ar);
	}

}
