package com.weblearn.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/** 
* 使用ExecutorCompletionService管理异步任务 
* 1. Java中的ExecutorCompletionService<V>本身有管理任务队列的功能 
* i. ExecutorCompletionService内部维护列一个队列, 用于管理已完成的任务 
* ii. 内部还维护列一个Executor, 可以执行任务 * 
* 2. ExecutorCompletionService内部维护了一个BlockingQueue, 只有完成的任务才被加入到队列中 * 
* 3. 任务一完成就加入到内置管理队列中, 如果队列中的数据为空时, 调用take()就会阻塞 (等待任务完成) 
* i. 关于完成任务是如何加入到完成队列中的, 请参考ExecutorCompletionService的内部类QueueingFuture的done()方法 * 
* 4. ExecutorCompletionService的take/poll方法是对BlockingQueue对应的方法的封装, 关于BlockingQueue的take/poll方法: 
* i. take()方法, 如果队列中有数据, 就返回数据, 否则就一直阻塞; * ii. poll()方法: 如果有值就返回, 否则返回null 
* iii. poll(long timeout, TimeUnit unit)方法: 如果有值就返回, 否则等待指定的时间; 如果时间到了如果有值, 就返回值, 否则返回null * 
* 解决了已完成任务得不到及时处理的问题 
*/ 
public class ExecutorCompletionServiceSample {

	public static void main(String[] args) throws InterruptedException, ExecutionException { 
		Random random = new Random(); 
		ExecutorService service = Executors.newFixedThreadPool(10); 
		ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(service);

		for (int i = 0; i < 50; i++) {
			completionService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(random.nextInt(5000));
					return Thread.currentThread().getName();
				}
			});
		}

		int completionTask = 0; while(completionTask < 50) { 
			//如果完成队列中没有数据, 则阻塞; 否则返回队列中的数据 
			Future<String> resultHolder = completionService.take();
			System.out.println("result: " + resultHolder.get());
			completionTask++;
		}
		System.out.println(completionTask + " task done !"); 
		// ExecutorService使用完一定要关闭 (回收资源, 否则系统资源耗尽! .... 呵呵...)
		service.shutdown();
	}
	
}
