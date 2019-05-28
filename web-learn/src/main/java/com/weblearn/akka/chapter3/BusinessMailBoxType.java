package com.weblearn.akka.chapter3;

import com.typesafe.config.Config;
import com.weblearn.akka.chapter2.WorkerActor;
import com.weblearn.akka.utils.ActorSystemManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import akka.dispatch.ProducesMessageQueue;
import scala.Option;

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
		ActorRef worker = system.actorOf(Props.create(WorkerActor.class).withDispatcher("business-mailbox"), "workerActor");
		for(int i = 0 ; i < 20; i++) {		
			worker.tell("mailbox", ActorRef.noSender());
		}
	}
}
