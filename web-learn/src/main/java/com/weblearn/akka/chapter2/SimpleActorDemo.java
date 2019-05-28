package com.weblearn.akka.chapter2;

import akka.actor.UntypedActor;
import akka.japi.Procedure;
import lombok.AllArgsConstructor;
import lombok.Data;

public class SimpleActorDemo extends UntypedActor {

	Procedure<Object> LEVEL1 = new Procedure<Object>() {
		@Override
		public void apply(Object message) throws Exception {
			if(message instanceof String && "end".equals(message)) {
				getContext().unbecome();
			} else {
				
			}
		}		
	};
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}
	
	@Data
	@AllArgsConstructor
	public static class Emp {
		
		private String name;
		
		private Integer salary;
		
		
	}
}
