package com.weblearn.thread.chapter4;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ResultTask extends FutureTask<String> {

	private String name;
	
	public ResultTask(Callable<String> callable) {
		super(callable);
		this.name = ((ExecutableTask) callable).getName();
	}

	@Override
	protected void done() {
		if(isCancelled()) {
			System.out.printf("%s: Has been canceled\n", name);
		} else {
			System.out.printf("%s: Has finished\n", name);
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();
		ResultTask resultTasks[] = new ResultTask[5];
		
		for(int i = 0; i < 5; i++) {
			ExecutableTask executableTask = new ExecutableTask("Task" + i);
			resultTasks[i] = new ResultTask(executableTask);
			executor.submit(resultTasks[i]);
		}
		
		try { 
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException el) {
			el.printStackTrace();
		}
		
		for(int i = 0; i < resultTasks.length; i++) {
			resultTasks[i].cancel(true);
		}
		
		for(int i = 0; i < resultTasks.length; i++) {
			try {
				if(!resultTasks[i].isCancelled()) {
					System.out.printf("%s\n", resultTasks[i].get());
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
	}
}
