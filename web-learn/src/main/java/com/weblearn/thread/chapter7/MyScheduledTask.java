package com.weblearn.thread.chapter7;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyScheduledTask<V> extends FutureTask<V> implements
		RunnableScheduledFuture<V> {

	private RunnableScheduledFuture<V> task;
	
	private ScheduledThreadPoolExecutor executor;
	
	private long period;
	
	private long startDate;
	
	public MyScheduledTask(Runnable runnable, V result,
			RunnableScheduledFuture<V> task, ScheduledThreadPoolExecutor executor) {
		super(runnable, result);
		this.task = task;
		this.executor = executor;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		if(!isPeriodic()) {
			return task.getDelay(unit);
		} else {
			if(startDate == 0) {
				return task.getDelay(unit);
			} else {
				Date now = new Date();
				long delay = startDate - now.getTime();
				return unit.convert(delay, TimeUnit.MILLISECONDS);
			}
		}
		
	}

	@Override
	public int compareTo(Delayed o) {	
		return task.compareTo(o);
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		
		return null;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPeriodic() {
		// TODO Auto-generated method stub
		return false;
	}

}
