package com.weblearn.thread.chapter7;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyPriorityTaskSample {

	public static void main(String[] args) {
		ThreadPoolExecutor executor = 
				new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());

		//List<MyPriorityTask> tasks = new ArrayList<MyPriorityTask>();
		for(int i = 0 ; i < 100; i++) {
			MyPriorityTask task = new MyPriorityTask("Task" + i, i);
			executor.execute(task);
		}
		
		executor.shutdown();
		try{
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Main : End of the program.");
	}

}
