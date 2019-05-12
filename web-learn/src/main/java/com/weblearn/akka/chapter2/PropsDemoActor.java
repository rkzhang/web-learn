package com.weblearn.akka.chapter2;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class PropsDemoActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(msg.toString());
	}

	public static Props createProps() {
		
		return Props.create(new Creator<PropsDemoActor>() {

			private static final long serialVersionUID = 1L;

			@Override
			public PropsDemoActor create() throws Exception {
				
				return new PropsDemoActor();
			}
			
		});
	}
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("sys");
		ActorRef actorRef = system.actorOf(PropsDemoActor.createProps(), "propsActor");
		actorRef.tell("Hello Akko - PropsDemoActor", ActorRef.noSender());
	}
}
