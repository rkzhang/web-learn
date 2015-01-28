package com.weblearn.thread.chapter5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class RecursiveTaskSample {

	public static void main(String[] args) {
		Document mock = new Document();
		String[][] document = mock.generateDocument(100, 1000, "the");
		
		DocumentTask task = new DocumentTask(document, 0, 100, "the");
		
		ForkJoinPool pool = new ForkJoinPool(10);
		
		//execute是异步调用的， invoke方法是同步调用的。
		pool.execute(task);
		
		do {
			System.out.println("*********************");
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
			System.out.println("*********************");
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while(!task.isDone());
		
		pool.shutdown();
		
		try {
			pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.printf("Main: The word appears %d in the document", task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
