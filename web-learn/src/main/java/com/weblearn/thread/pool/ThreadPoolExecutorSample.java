package com.weblearn.thread.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.weblearn.thread.chapter7.MyThreadFactory;

/*
 * 判断核心线程数是否已满，核心线程数大小和corePoolSize参数有关，未满则创建线程执行任务
 * 若核心线程池已满，判断队列是否满，队列是否满和workQueue参数有关，若未满则加入队列中
 * 若队列已满，判断线程池是否已满，线程池是否已满和maximumPoolSize参数有关，若未满创建线程执行任务
 * 若线程池已满，则采用拒绝策略处理无法执执行的任务，拒绝策略和handler参数有关
 */
public class ThreadPoolExecutorSample {

	public static void main(String[] args) {
		MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");
		//corePoolSize => 线程池核心线程数量
		//maximumPoolSize => 线程池最大数量
		//keepAliveTime => 空闲线程存活时间
		//unit => 时间单位
		//workQueue => 线程池所使用的缓冲队列
		//threadFactory => 线程池创建线程使用的工厂
		//handler => 线程池对拒绝任务的处理策略
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100), threadFactory);
		
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
	        
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, executor);

		System.out.println("main...end....");
	}
}
