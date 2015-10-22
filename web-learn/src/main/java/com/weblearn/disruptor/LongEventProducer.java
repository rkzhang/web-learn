package com.weblearn.disruptor;

import java.nio.ByteBuffer;
import com.lmax.disruptor.RingBuffer;

/**
 * 事件都会有一个生成事件的源，这个例子中假设事件是由于磁盘IO或者network读取数据的时候触发的，
 * 事件源使用一个ByteBuffer来模拟它接受到的数据，也就是说，事件源会在IO读取到一部分数据的时候触发事件
 * （触发事件不是自动的，程序员需要在读取到数据的时候自己触发事件并发布）：
 * @author rkzhang
 */
public class LongEventProducer {

	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	/**
	 * onData用来发布事件，每调用一次就发布一次事件事件 它的参数会通过事件传递给消费者
	 * 
	 * @param bb
	 */
	public void onData(ByteBuffer bb) {
		// 可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
		long sequence = ringBuffer.next();
		try {
			// 用上面的索引取出一个空的事件用于填充
			LongEvent event = ringBuffer.get(sequence);// for the sequence
			event.setValue(bb.getLong(0));
		} finally {
			// 发布事件
			ringBuffer.publish(sequence);
		}
	}
}
