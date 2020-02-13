package com.syntaxphoenix.syntaxapi.threading;

import java.util.concurrent.ThreadFactory;

public class SynThreadFactory implements ThreadFactory {
	
	private final String poolName;
	private int count = 0;

	public SynThreadFactory(String poolName) {
		this.poolName = poolName;
	}
	
	public final String getName() {
		return poolName;
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
		Thread thread = new Thread(command);
		thread.setName(nextName());
		return thread;
	}

}
