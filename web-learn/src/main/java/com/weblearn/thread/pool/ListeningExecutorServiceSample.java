package com.weblearn.thread.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


public class ListeningExecutorServiceSample {

	private ListeningExecutorService pool;

	@PostConstruct
	public void init() {
		pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

	}

	@PreDestroy
	public void destroy() {

		pool.shutdown();
	}
	
	public void execute() {
		ListenableFuture<String> f1 = pool.submit(new Callable<String>() {
	        @Override
	        public String call() throws Exception {
	            return "aaa";
	        }
	    });
	}
	
}
