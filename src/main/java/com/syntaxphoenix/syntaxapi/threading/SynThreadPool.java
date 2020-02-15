package com.syntaxphoenix.syntaxapi.threading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class SynThreadPool extends ThreadPoolExecutor {

	private final SynThreadReporter reporter;
	
	public SynThreadPool(SynThreadReporter reporter, int min, int max, String poolName) {
		super(min, max, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), (new SynThreadFactory(poolName)));
		if(min <= 0) {
			throw new IllegalArgumentException("Minimum thread count cannot be lower than 1");
		} else if(max < min || max <= 0) {
			throw new IllegalArgumentException("Maximum thread count need to be higher than 0 and higher than or equal to the minimum thread count!");
		}
		this.reporter = reporter;
	}
	
	@Override
	public final SynThreadFactory getThreadFactory() {
		return (SynThreadFactory) super.getThreadFactory();
	}
	
	public final String getName() {
		return getThreadFactory().getName();
	}
	
	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public void execute(Runnable command) {
		super.execute(() -> {
			try {
				command.run();
			} catch(Throwable throwable) {
				reporter.catchFail(throwable, this, Thread.currentThread(), command);
			}
		});
	}
	
}
