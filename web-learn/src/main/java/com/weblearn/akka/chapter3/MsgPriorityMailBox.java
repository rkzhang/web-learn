package com.weblearn.akka.chapter3;

import java.util.Comparator;
import akka.dispatch.Envelope;
import akka.dispatch.UnboundedStablePriorityMailbox;

public class MsgPriorityMailBox extends UnboundedStablePriorityMailbox {

	public MsgPriorityMailBox(Comparator<Envelope> cmp) {
		super(cmp);
		
	}



}
