package com.weblearn.akka.chapter6;

import com.weblearn.akka.utils.ActorSystemManager;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Awaitable;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class TestFuturesActor {

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		ActorRef ref = system.actorOf(Props.create(FutureActor.class), "fuActor");
		
		Timeout timeout = new Timeout(Duration.create(3, "seconds"));
		Future<Object> future = Patterns.ask(ref, "hello future", timeout);
		
		try {
			Awaitable<Object> replymsg =  Await.ready(future, timeout.duration());
			System.out.print(replymsg.result(Duration.create(5, "seconds"), null));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class FutureActor extends UntypedActor {

		@Override
		public void onReceive(Object msg) throws Exception {
			Thread.sleep(1000);
			getSender().tell("Future Actor Reply", getSelf());
		}		
	}

}
