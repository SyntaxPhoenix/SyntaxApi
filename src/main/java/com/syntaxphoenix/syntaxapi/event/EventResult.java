package com.syntaxphoenix.syntaxapi.event;

public final class EventResult {
	
	private final int count;
	private int executed = 0;
	
	private int successful = 0;
	private int cancelled = 0;
	private int failed = 0;
	
	public EventResult(int count) {
		this.count = count;
	}
	
	protected boolean cancelled() {
		if(executed + 1 > count) {
			return false;
		}
		executed++;
		cancelled++;
		return true;
	}
	
	protected boolean success() {
		if(executed + 1 > count) {
			return false;
		}
		executed++;
		successful++;
		return true;
	}
	
	protected boolean fail() {
		if(executed + 1 > count) {
			return false;
		}
		executed++;
		failed++;
		return true;
	}
	
	/*
	 * 
	 */
	
	public int executedCount() {
		return executed;
	}
	
	public int cancelledCount() {
		return cancelled;
	}
	
	public int successfulCount() {
		return successful;
	}
	
	public int failedCount() {
		return failed;
	}
	
	public int globalCount() {
		return count;
	}

}
