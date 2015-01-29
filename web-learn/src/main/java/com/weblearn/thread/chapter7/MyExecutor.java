package com.weblearn.thread.chapter7;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyExecutor extends ThreadPoolExecutor {
	
	private ConcurrentHashMap<String, Date> startTimes;

	public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		startTimes = new ConcurrentHashMap<String, Date>();
	}

	
	public void shutdown() {
		System.out.println("MyExecutor: Going to shutdown.");
		System.out.printf("MyExecutor: Executed tasks : %d\n", getCompletedTaskCount());
		System.out.printf("MyExecutor: Running tasks: %d\n", getActiveCount());
		System.out.printf("MyExecutor: Pending tasks: %d\n", getQueue().size());
		super.shutdown();
	}
	
	public List<Runnable> shutdownNow() {
		System.out.println("MyExecutor: Going to immediately shutdown.");
		System.out.printf("MyExecutor: Executed tasks: %d\n", getCompletedTaskCount());
		System.out.printf("MyExecutor: Running tasks: %d\n", getActiveCount());
		System.out.printf("MyExecutor: Pending tasks: %d\n", getQueue().size());
		return super.shutdownNow();
	}


	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		System.out.printf("MyExecutor: A task is beginning: %s : %s\n", t.getName(), r.hashCode());
		startTimes.put(String.valueOf(r.hashCode()), new Date());
		super.beforeExecute(t, r);
	}


	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		Future<?> result = (Future<?>)r;
		try {
			System.out.println("********************");
			System.out.println("MyExecutor: A task is finishing.");
			System.out.printf("MyExecutor: Result: %s\n", result.get());
			Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
			Date finishDate = new Date();
			long diff = finishDate.getTime() - startDate.getTime();
			System.out.printf("MyExecutor: Duration: %d\n", diff);
			System.out.println("********************");
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		super.afterExecute(r, t);
	}
	
	
}
