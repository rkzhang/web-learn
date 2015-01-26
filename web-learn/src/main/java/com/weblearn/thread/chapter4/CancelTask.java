package com.weblearn.thread.chapter4;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Task Cancel Sample
 * 关于cancel方法
 * 1，如果任务已经完成，或者之前已经被取消，或者由于某种原因而不能被取消，那么方法将返回false并且任务也不能取消。
 * 2，如果任务在执行器中等待分配Thread对象来执行它，那么任务被取消，并且不会开始执行。如果任务已经在运行，
 *   那么依赖于调用cancel方法时所传递的参数。如果传递的参数为true并且任务正在运行，那么任务将被取消。
 *   如果传递的参数为false并且任务正在运行，那么任务不会被取消。
 * @author rkzhang
 */
public class CancelTask implements Callable<String> {
	
	@Override
	public String call() throws Exception {
		while(true) {
			System.out.printf("Task: Test\n");
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
		CancelTask task = new CancelTask();
		
		System.out.printf("Main: Executing the Task\n");
		Future<String> result = executor.submit(task);
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Main: Canecling the Task\n");
		result.cancel(true);
		
		System.out.printf("Main: Cancelled: %s\n", result.isCancelled());
		System.out.printf("Main: Done: %s\n", result.isDone());
		
		executor.shutdown();
		System.out.printf("Main: The executor has finished\n");
	}
}
