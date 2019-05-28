package com.weblearn.akka.chapter3;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;

/**
 * 自定义队列实现
 * @author Rechard.Zhang
 */
public class BusinessMsgQueue implements MessageQueue {
	
	private Queue<Envelope> queue = new ConcurrentLinkedQueue<>();

	@Override
	public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
		for(Envelope el : queue) {
			deadLetters.enqueue(owner, el);
		}
	}

	/**
	 * 取出消息
	 */
	@Override
	public Envelope dequeue() {
		return queue.poll();
	}

	/**
	 * 投递消息
	 */
	@Override
	public void enqueue(ActorRef receiver, Envelope el) {
		System.out.println("receive ---> " + el.toString());
		queue.offer(el);
		System.out.println("queue size ---> " + queue.size());
	}

	/**
	 * 判断是否有消息
	 */
	@Override
	public boolean hasMessages() {
		return !queue.isEmpty();
	}

	/**
	 * 获取数量
	 */
	@Override
	public int numberOfMessages() {
		return queue.size();
	}

}
