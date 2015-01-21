package com.weblearn.thread;

import java.util.concurrent.ThreadFactory;

public class SampleThreadFactory implements ThreadFactory {

	private Integer num = 1;
	
	@Override
	public Thread newThread(Runnable r) {
		System.out.println("create thread --- " + num);
		return new Thread(new ProcessorSample(num++));
	}

}
