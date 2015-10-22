package com.weblearn.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 需要让Disruptor为我们创建事件，声明了一个EventFactory来实例化Event对象。
 * @author rkzhang
 */
public class LongEventFactory implements EventFactory<LongEvent> {

	@Override
	public LongEvent newInstance() {
		
		return new LongEvent();
	}

}
