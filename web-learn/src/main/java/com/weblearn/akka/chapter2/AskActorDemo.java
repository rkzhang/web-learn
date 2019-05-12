package com.weblearn.akka.chapter2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class AskActorDemo extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("发送者是 : " + getSender());
		getSender().tell("hello " + msg, getSelf());
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef ask_ar = system.actorOf(Props.create(AskActorDemo.class), "askActorDemo");
		
		Timeout timeout = new Timeout(Duration.create(3, "seconds"));
		Future<Object> f = Patterns.ask(ask_ar, "Akka Ask", timeout);
		System.out.println("ask ...");
		
		f.onSuccess(new OnSuccess() {
			@Override
			public void onSuccess(Object result) throws Throwable {
				System.out.println("收到消息 : " + result);
			}
		
		}, system.dispatcher());
		
		System.out.println("continue ... ");
	}
}
