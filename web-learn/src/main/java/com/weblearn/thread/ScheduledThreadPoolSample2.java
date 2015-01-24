package com.weblearn.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolSample2 {

	public static void main(String[] args) {
		ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);  

		threadPool.scheduleAtFixedRate(new MyScheduledTask("Schedule At Fixed Rate Task"), 3, 10, TimeUnit.SECONDS);
		threadPool.scheduleWithFixedDelay(new MyScheduledTask("Schedule With Fixed Rate Task"), 3, 10, TimeUnit.SECONDS);
		
	}

}
