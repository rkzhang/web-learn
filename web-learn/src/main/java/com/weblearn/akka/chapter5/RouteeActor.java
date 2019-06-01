package com.weblearn.akka.chapter5;

import akka.actor.UntypedActor;

public class RouteeActor extends UntypedActor {

	@Override
	public void onReceive(Object msg ) throws Exception {
		System.out.println(getSelf() + " ---> " + msg);
	}

}
