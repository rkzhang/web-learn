package com.weblearn.akka.utils;

import java.util.Optional;

import com.weblearn.akka.chapter2.SupervisorActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public final class ActorSystemManager {

	private static final ActorSystem system = ActorSystem.create("sys");
	
	public static ActorSystem getActorSystem() {
		return system;
	}
	
	public static Optional<ActorRef> lookupActor(String path) {
		return lookupActor(path, null, null);
	}
	
	public static Optional<ActorRef> lookupActor(String path, OnSuccess<ActorRef> onSuccess, OnFailure onFailure) {
		ActorSystem system = ActorSystem.create("sys");

		ActorSelection as = system.actorSelection(path);
		Timeout timeout = new Timeout(Duration.create(3, "seconds"));
		Future<ActorRef> fu = null;
		try {
			fu = as.resolveOne(timeout);
			if(onSuccess != null) {
				fu.onSuccess(onSuccess, system.dispatcher());
			}
			
			if(onFailure != null) {
				fu.onFailure(onFailure, system.dispatcher());
			}
		} catch (Exception e) {
			return Optional.empty();
		}
		
		while(!fu.isCompleted()) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return Optional.empty();
			}
		}
		return Optional.of(fu.value().get().get());
	}
}
