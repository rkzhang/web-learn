package com.weblearn.thread.chapter3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyslicBarrierSample {

	
	public class Job implements Runnable {

		private CyclicBarrier barrier;
		
		public Job(CyclicBarrier barrier) {
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			System.out.printf("%s: Going to do a job\n", Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep((int)(Math.random() * 10));
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.printf("%s: The job has been done\n", Thread.currentThread().getName());
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		CyslicBarrierSample sample = new CyslicBarrierSample();
		CyclicBarrier barrier = new CyclicBarrier(10);
		Thread thread[] = new Thread[10];
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(sample.new Job(barrier), "Thread" + i);
		}
		
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
		
		for (int i = 0; i < 10; i++) {
			thread[i].join();
		}
		
		for(int t = 0; t < 5; t++) {
			System.out.println("process ---------- " + t);
			
			barrier.reset();
			for (int i = 0; i < 10; i++) {
				thread[i] = new Thread(sample.new Job(barrier), "Thread" + i);
			}
			
			for (int i = 0; i < 10; i++) {
				thread[i].start();
			}
			
			for (int i = 0; i < 10; i++) {
				thread[i].join();
			}
		}
	}
}
