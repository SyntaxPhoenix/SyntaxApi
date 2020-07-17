package com.syntaxphoenix.syntaxapi.thread;

import java.util.concurrent.ThreadFactory;

public class SynThreadFactory implements ThreadFactory, SynReportThrower {

	private final SynThreadReporter reporter;

	private final String poolName;
	private int count = 0;

	public SynThreadFactory(String poolName) {
		this.poolName = poolName;
		this.reporter = null;
	}

	public SynThreadFactory(String poolName, SynThreadReporter reporter) {
		this.poolName = poolName;
		this.reporter = reporter;
	}

	public final String getName() {
		return poolName;
	}

	public final SynThreadReporter getReporter() {
		return reporter;
	}

	public int getCreatedThreadCount() {
		return count;
	}

	private final String nextName() {
		String name = poolName + " - " + count;
		count++;
		return name;
	}

	@Override
	public Thread newThread(Runnable command) {
		Thread thread;
		if (reporter != null) {
			thread = new Thread(() -> {
				try {
					command.run();
				} catch (Throwable throwable) {
					reporter.catchFail(throwable, this, Thread.currentThread(), command);
				}
			});
		} else {
			thread = new Thread(command);
		}
		thread.setName(nextName());
		return thread;
	}

	/*
	 * 
	 */

	@Override
	public final boolean isPool() {
		return false;
	}

}
