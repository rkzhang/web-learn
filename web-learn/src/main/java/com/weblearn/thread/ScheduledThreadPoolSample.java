package com.weblearn.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolSample {

	public static void main(String[] args) {
		ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);  
        ScheduledExecutorService singleThread = Executors.newSingleThreadScheduledExecutor();  
          
        threadPool.schedule(new MyScheduledTask("t1"), 1, TimeUnit.SECONDS);  
        threadPool.schedule(new MyScheduledTask("t2"), 1, TimeUnit.SECONDS);   // t1、t2在多个线程执行t1延时不会影响t2  
        singleThread.schedule(new MyScheduledTask("t3"), 1, TimeUnit.SECONDS);  
        singleThread.schedule(new MyScheduledTask("t4"), 1, TimeUnit.SECONDS); // t3、t4 在同一线程执行t3延时会影响 t4  
          
        threadPool.shutdown();  
        singleThread.shutdown();  
		
	}
}

class MyScheduledTask implements Runnable {  
    
    private String tname;  
      
    public MyScheduledTask(String tname) {  
        this.tname = tname;  
    }  
  
    @Override  
    public void run() {  
        System.out.println(tname+"任务开始执行");  
        try {  
            Thread.sleep(1000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println(tname+"任务执行完毕！！！");  
    }  
      
}  