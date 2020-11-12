package com.weblearn.thread.chapter7;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

	private volatile long counter;
	
	private String prefix;
	
	public MyThreadFactory(String prefix) {
		this.prefix = prefix;
		counter = 1;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		MyThread myThread = new MyThread(r, prefix + "-" + counter);
		counter++;
		myThread.setStartDate();
		return myThread;
	}

}
