package com.weblearn.akka.chapter6;

import akka.actor.ActorRef;
import akka.event.japi.LookupEventBus;

public class EventBusDemo extends LookupEventBus<Event, ActorRef, String> {

	@Override
	public String classify(Event event) {
		return event.getType();
	}

	@Override
	public int compareSubscribers(ActorRef ref1, ActorRef ref2) {
		return ref1.compareTo(ref2);
	}

	@Override
	public int mapSize() {
		return 8;
	}

	public void publish(Event event, ActorRef ref) {
		ref.tell(event.getMessage(), ActorRef.noSender());
	}

}
