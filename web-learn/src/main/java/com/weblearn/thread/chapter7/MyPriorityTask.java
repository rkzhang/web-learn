package com.weblearn.thread.chapter7;

import java.util.concurrent.TimeUnit;

public class MyPriorityTask implements Runnable, Comparable<MyPriorityTask> {
	
	private int priority;
	
	private String name;
	
	public MyPriorityTask(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	@Override
	public void run() {
		System.out.printf("MyPriorityTask: %s Priority : %d\n", name, priority);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int compareTo(MyPriorityTask o) {
		return this.getPriority() == o.getPriority() ? 0 : this.getPriority() < o.getPriority() ? -1 : 1;
	}

	public int getPriority() {
		return priority;
	}

}
