package com.weblearn.akka.chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.typesafe.config.Config;
import com.weblearn.akka.chapter2.AskActorDemo;
import com.weblearn.akka.utils.ActorSystemManager;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import akka.dispatch.OnSuccess;
import akka.dispatch.ProducesMessageQueue;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.Option;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class BusinessMailBoxType implements MailboxType, ProducesMessageQueue<BusinessMsgQueue> {

	public BusinessMailBoxType(ActorSystem.Settings settings, Config config) {
		
	}
	
	@Override
	public MessageQueue create(Option<ActorRef> ref, Option<ActorSystem> opt) {
		System.out.println("create mail box ---> " + ref.get().path().name());
		return new BusinessMsgQueue();
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystemManager.getActorSystem();
		ActorRef worker1 = system.actorOf(Props.create(AskActorDemo.class).withDispatcher("business-mailbox"), "workerActor1");
		ActorRef worker2 = system.actorOf(Props.create(AskActorDemo.class).withDispatcher("business-mailbox"), "workerActor2");
		ExecutorService es = Executors.newCachedThreadPool();
		for(int i = 0 ; i < 20; i++) {		
			final int a = i;
			Thread t1 = new Thread(() -> {		
				String req = "Akka Ask" + a;
				Timeout timeout = new Timeout(Duration.create(5, "seconds"));
				Future<Object> f = null;
				if(a % 2 == 0) {
					f = Patterns.ask(worker2, req, timeout);
				} else {
					f = Patterns.ask(worker1, req, timeout);
				}
				f.onSuccess(new OnSuccess<Object>() {
					@Override
					public void onSuccess(Object result) throws Throwable {
						System.out.println("-----------------------");
						System.out.println("r 请求消息 ---> " + req);
						System.out.println("r 收到消息 ---> " + result);
						System.out.println("-----------------------");
					}
				
				}, system.dispatcher());
			});
			es.execute(t1);
		}
		
	}
}
