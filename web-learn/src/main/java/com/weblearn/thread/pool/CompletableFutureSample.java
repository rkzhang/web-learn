package com.weblearn.thread.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureSample {

	public static ExecutorService executor = Executors.newFixedThreadPool(10);

	/**
	 * runAsync
	 * 
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void runAsync() throws ExecutionException, InterruptedException {
		System.out.println("main...start....");

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

			System.out.println("当前线程：" + Thread.currentThread().getId());
			int i = 10 / 2;
			System.out.println("运行结果：" + i);
		}, executor);

		System.out.println("main...end....");

	}

	/**
	 * supplyAsync
	 * whenComplete（完成回调），exceptionally(异常感知)
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void supplyAsync() throws InterruptedException, ExecutionException {
		System.out.println("main...start....");
		/**
		 * 方法完成后的感知
		 */
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("当前线程：" + Thread.currentThread().getId());
			int i = 10 / 2;
			System.out.println("运行结果：" + i);
			return i;
		}, executor).whenComplete((res, exception) -> {
			// 虽然能得到异常信息，但是没法修改返回数据
			System.out.println("异步任务成功完成了...结果是：" + res + "异常是：" + exception);
		}).exceptionally(throwable -> {
			// 可以感知异常，同时返回默认值
			return 10;
		});
		Integer integer = future.get();
		System.out.println("main...end...." + integer);
	}
	
	/**
	 * handle（最终处理）
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void handle() throws InterruptedException, ExecutionException{
		System.out.println("main...start....");
       	/**
         * 方法执行完成后的处理
         */
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 4;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).handle((res, thr) -> {
            if (res != null) {
                return res * 2;
            }
            if (thr != null) {
                return 0;
            }
            return 0;
        });
        Integer integer=future.get();
		System.out.println("main...end...."+integer);
 	}
	
	/**
	 * 线程串行化
     * 1）、thenRun：不能获取到上一步的执行结果，无返回值
     * .thenRunAsync(() -> {
     *             System.out.println("任务2启动了...");
     *         }, executor);
     * 2)、能接受上一步结果，但是无返回值
     *  .thenAcceptAsync(res -> {
     *             System.out.println("任务2启动了..." + res);
     *         }, executor);
     * 3)、thenApplyAsync：能接受上一步结果，有返回值
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     */
	public static void thenApplyAsync() throws InterruptedException, ExecutionException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 4;
            System.out.println("运行结果：" + i);
            return i;
        }, executor).thenApplyAsync(res -> {
            System.out.println("任务2启动了..." + res);
            return "Hello" + res;
        }, executor);

		System.out.println("main...end...."+future.get());
	}
	
	/**
	 * 两任务组合——都完成
	 */
	public static void thenCombineAsync() {
		CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 4;
            System.out.println("任务1结束：" + i);
            return i;
        }, executor);

        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2结束：");
            return "Hello";
        }, executor);

        future01.runAfterBothAsync(future02,()->{
            System.out.println("任务3开始...");
        },executor);

        future01.thenAcceptBothAsync(future02, (f1, f2) -> {
            System.out.println("任务3开始...之前的结果：" + f1 + "--->" + f2);
        }, executor);
        
        CompletableFuture<String> future = future01.thenCombineAsync(future02, (f1, f2) -> {
            return f1 + ":" + f2 + "-> Haha";
        }, executor);
	}
	
	/**
	 * 两任务组合——一个完成
	 */
	public static void applyToEitherAsync() {
		CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 4;
            System.out.println("任务1结束：" + i);
            return i;
        }, executor);

        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2结束：");
            return "Hello";
        }, executor);
   		/**
         * 两个任务，只要有一个完成，我们就执行任务3
         * runAfterEitherAsync：不感知结果，自己也无返回值
         * acceptEitherAsync：感知结果，自己没有返回值
         */
        future01.runAfterEitherAsync(future02, () -> {
            System.out.println("任务3开始...之前的结果：");
        }, executor);

        future01.acceptEitherAsync(future02,(res)->{
            System.out.println("任务3开始...之前的结果："+res);
        },executor);

        CompletableFuture<String> future = future01.applyToEitherAsync(future02, (res) -> {
            System.out.println("任务3开始...之前的结果：" + res);
            return res.toString() + "->哈哈";
        }, executor);
	}
	
	/**
	 * 多任务组合
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<String> future1= CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1");
            return "任务1结果";
        },executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2");
            return "任务2结果";
        },executor);
        
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务3");
            return "任务3结果";
        },executor);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2 , future3 );
		allOf.get();	//等待所有结果完成
		
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2 , future3);
        anyOf.get();
        System.out.println("main...end..."+anyOf.get());
	}
}
