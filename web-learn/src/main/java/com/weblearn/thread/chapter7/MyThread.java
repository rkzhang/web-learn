package com.weblearn.thread.chapter7;

import java.util.Date;

public class MyThread extends Thread {

	private Date creationDate;
	
	private Date startDate;
	
	private Date finishDate;
	
	public MyThread(Runnable target, String name) {
		super(target, name);
		setCreationDate();
	}
	
	@Override
	public void run() {
		setStartDate();
		super.run();
		setFinishDate();
		System.out.println("Main: Thread information.");
		System.out.printf("%s\n", this);
		System.out.println("Main: End of the example.");
	}

	public void setCreationDate() {
		creationDate = new Date();
	}
	
	public void setStartDate() {
		startDate = new Date();
	}
	
	public void setFinishDate() {
		finishDate = new Date();
	}
	
	public long getExecutionTime() {
		return finishDate.getTime() - startDate.getTime();
	}
	
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(this.getName());
		buffer.append(": ");
		buffer.append(" Creation Date : ");
		buffer.append(creationDate);
		buffer.append(" : Running time: ");
		buffer.append(getExecutionTime());
		buffer.append(" Milliseconds.");
		return buffer.toString();
	}
}
