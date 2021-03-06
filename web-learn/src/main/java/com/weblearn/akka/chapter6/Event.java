package com.weblearn.akka.chapter6;

public class Event {

	private String type;
	
	private String message;
	
	public Event(String type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public String getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
}
