package com.weblearn.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPoolSample {
	
	private static List<ProcessorSample> sampleList = new ArrayList<>();
	
	private static ProcessorSample t;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(5, new SampleThreadFactory());
		es.execute(new ProcessorSample(1));
		
		for (int i = 0; i < 10; i++) {
			t = new ProcessorSample(i);
			es.execute(t);
	
		} 
		
		// 关闭启动线程
		es.shutdown();
        // 等待子线程结束，再继续执行下面的代码
		es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("all thread complete");
        
	}
}
