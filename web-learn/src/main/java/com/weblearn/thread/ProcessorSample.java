package com.weblearn.thread;

public class ProcessorSample implements Runnable {
	
	private Integer tNumber;
	
	public ProcessorSample(Integer num) {
		 this.tNumber = num;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 5; i++) {
			 System.out.println(tNumber + " thread ");
			 try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Integer gettNumber() {
		return tNumber;
	}

	public void settNumber(Integer tNumber) {
		this.tNumber = tNumber;
	}

}
