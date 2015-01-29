package com.weblearn.thread.chapter5;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ExceptionTaskSample extends RecursiveTask<Integer>{

	private static final long serialVersionUID = -5683819650464026108L;

	private int array[];
	
	private int start, end;
	
	public ExceptionTaskSample(int array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		System.out.printf("Task: Start from %d to %d\n", start, end);
		if(end - start < 10) {
			if((3 > start) && (3 < end)) {
				throw new RuntimeException("This task throws an " + 
						"Exception: Task from " + start + " to " + end);
				
				//不抛异常，同样效果
				//Exception e = new Exception("This task throws an Exception: Task from " + start + " to " + end);
				//completeExceptionally(e);
			}
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		} else {
			int mid = (end + start) / 2;
			ExceptionTaskSample task1 = new ExceptionTaskSample(array, start, mid);
			ExceptionTaskSample task2 = new ExceptionTaskSample(array, mid, end);
			invokeAll(task1, task2);
		}
		System.out.printf("Task: End form %d to %d\n", start, end);
		return 0;
	}

	public static void main(String[] args) {
		int array[] = new int[100];
		ExceptionTaskSample task = new ExceptionTaskSample(array, 0, 100);
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
				
		pool.shutdown();
		try {
			pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//如果主任务或者它的子任务之一抛出了异常，这个方法将返回true.也可以使用getException()方法来获得抛出的Exception对象。
		if(task.isCompletedAbnormally()) {
			System.out.println("Main: An exception has ocurred");
			System.out.printf("Main: %s\n", task.getException());
		}
		
		System.out.printf("Main: Result: %d", task.join());
	}

}
