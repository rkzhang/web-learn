package com.weblearn.thread.chapter3;

import java.util.concurrent.Phaser;

public class MyPhaser extends Phaser {

	public MyPhaser(int i) {
		super(i);
	}

	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		System.out.println("onAdvance --- begin process step " + phase);
		return super.onAdvance(phase, registeredParties);
	}

}
