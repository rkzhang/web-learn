package com.weblearn.akka.chapter2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class Message {

	private final int visitCount;
	
	private final List<String> usernames;
	
	private final Map<String, String> cities;
	
	public Message(int visitCount, List<String> usernames, Map<String, String> cities) {
		this.visitCount = visitCount;
		this.usernames = Collections.unmodifiableList(usernames);
		this.cities = Collections.unmodifiableMap(cities);
	}

	public int getVisitCount() {
		return visitCount;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public Map<String, String> getCities() {
		return cities;
	}
	
}
