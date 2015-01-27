package com.weblearn.thread.chapter3;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserSample {

	
	public class Job implements Runnable {

		private Phaser phaser;
		
		public Job(Phaser phaser) {
			this.phaser = phaser;
		}
		
		@Override
		public void run() {
			phaser.arriveAndAwaitAdvance();
			System.out.printf("%s: Going to do fist step of job \n", Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep((int)(Math.random() * 10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			System.out.printf("%s: The fist step has been done\n", Thread.currentThread().getName());
			phaser.arriveAndAwaitAdvance();
			
			System.out.printf("%s: Going to do second step of job \n", Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep((int)(Math.random() * 10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			System.out.printf("%s: The second step has been done\n", Thread.currentThread().getName());
			
			phaser.arriveAndAwaitAdvance();
			
			System.out.printf("%s: Going to do third step of job \n", Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep((int)(Math.random() * 10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			System.out.printf("%s: The third step has been done\n", Thread.currentThread().getName());
			
			phaser.arriveAndDeregister();
		}
		
	}
	
	public static void main(String[] args) {
		PhaserSample phaserSameple = new PhaserSample();
		Phaser phaser = new MyPhaser(10);
		
		Thread thread[] = new Thread[10];
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(phaserSameple.new Job(phaser), "Thread" + i);
		}
		
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
		
	}
}
