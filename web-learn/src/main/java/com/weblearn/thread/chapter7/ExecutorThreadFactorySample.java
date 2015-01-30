package com.weblearn.thread.chapter7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadFactorySample {

	public static void main(String[] args) throws InterruptedException {
		MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");
		ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
		MyTask task = new MyTask();
		executor.submit(task);
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Main : End of the program.");
	}
}
