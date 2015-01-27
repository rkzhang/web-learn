package com.weblearn.thread.chapter4;

import java.util.concurrent.CompletionService;

public class ReportRequest implements Runnable {

	private String name;
	
	private CompletionService<String> serivce;
	
	public ReportRequest(String name, CompletionService<String> service) {
		this.name = name;
		this.serivce = service;
	}
	
	@Override
	public void run() {
		ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
		serivce.submit(reportGenerator);
	}

}
