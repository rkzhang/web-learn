package com.weblearn.thread.chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = -4213061294715784487L;

	private String document[][];
	
	private int start, end;
	
	private String word;
	
	public DocumentTask(String document[][], int start, int end, String word) {
		this.document = document;
		this.start = start;
		this.end = end;
		this.word = word;
	}
	
	@Override
	protected Integer compute() {
		int result = 0;
		if(end - start < 10) {
			result = processLines(document, start, end, word);
		} else {
			int mid = (start + end) / 2;
			DocumentTask task1 = new DocumentTask(document, start, mid, word);
			DocumentTask task2 = new DocumentTask(document, mid, end, word);
			
			//这是个同步调用，这个任务将等待子任务完成，然后继续执行(也可能是结束).当一个主任吴等待它的子任务时，
			//执行这个主任吴的工作者线程接收另一个等待执行的任务并开始执行
			invokeAll(task1, task2);
			
			try {
				result = groupResults(task1.get(), task2.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Integer processLines(String[][] document, int start, int end, String word) {
		List<LineTask> tasks = new ArrayList<LineTask>();
		for(int i = start; i < end; i++) {
			LineTask task = new LineTask(document[i], 0, document[i].length, word);
			tasks.add(task);
		}
		
		invokeAll(tasks);
		
		int result = 0;
		for(int i = 0; i < tasks.size(); i++) {
			LineTask task = tasks.get(i);
			try {
				result = result + task.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return new Integer(result);
	}
	
	private Integer groupResults(Integer number1, Integer number2) {
		Integer result = number1 + number2;
		return result;
	}

}
