package com.weblearn.thread.chapter1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataSourcesLoader implements Runnable {

	@Override
	public void run() {
		System.out.printf("Beginning data source loading: %s\n", new Date());
		try {
			TimeUnit.SECONDS.sleep(5);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.printf("Data sources loading has finished: %s\n", new Date());
	}
	
	public static void main(String[] args) {
		DataSourcesLoader dsLoader1 = new DataSourcesLoader();
		Thread thread1 = new Thread(dsLoader1, "DataSourceThread1");
		DataSourcesLoader dsLoader2 = new DataSourcesLoader();
		Thread thread2 = new Thread(dsLoader2, "DataSourceThread2");
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Main : Configuration has been loaded: %s\n", new Date());
	}

}
