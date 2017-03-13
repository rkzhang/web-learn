package com.weblearn.thread;

import java.util.concurrent.ThreadFactory;

public class SampleThreadFactory implements ThreadFactory {

	
	@Override
	public Thread newThread(Runnable r) {
		//ProcessorSample sample = (ProcessorSample)r;
		System.out.println("create thread --- " + r.getClass().getSimpleName());
		return new Thread(r);
	}

}
