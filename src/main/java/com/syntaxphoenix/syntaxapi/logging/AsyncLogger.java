package com.syntaxphoenix.syntaxapi.logging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncLogger implements ILogger {

	private final ExecutorService executor;
	private final ILogger logger;

	public AsyncLogger(ILogger logger) {
		this(Executors.newSingleThreadExecutor(), logger);
	}

	public AsyncLogger(ExecutorService executor, ILogger logger) {
		this.executor = executor;
		this.logger = logger;
	}
	
	/*
	 * 
	 */
	
	public ExecutorService getExecutor() {
		return executor;
	}
	
	public ILogger getLogger() {
		return logger;
	}
	
	/*
	 * 
	 */
	
	public void setThreadName(String name) {
		logger.setThreadName(name);
	}

	public void setState(LoggerState state) {
		logger.setState(state);
	}
	
	public String getThreadName() {
		return logger.getThreadName();
	}

	public LoggerState getState() {
		return logger.getState();
	}
	
	/*
	 * 
	 */

	public void log(String message) {
		queue(() -> logger.log(message));
	}

	public void log(LogType type, String message) {
		queue(() -> logger.log(type, message));
	}

	public void log(String typeId, String message) {
		queue(() -> logger.log(typeId, message));
	}

	public void log(String... messages) {
		queue(() -> logger.log(messages));
	}

	public void log(LogType type, String... messages) {
		queue(() -> logger.log(type, messages));
	}

	public void log(String typeId, String... messages) {
		queue(() -> logger.log(typeId, messages));
	}

	public void log(Throwable throwable) {
		queue(() -> logger.log(throwable));
	}

	public void log(LogType type, Throwable throwable) {
		queue(() -> logger.log(type, throwable));
	}

	public void log(String typeId, Throwable throwable) {
		queue(() -> logger.log(typeId, throwable));
	}
	
	/*
	 * 
	 */
	
	private void queue(Runnable runnable) {
		final String name = Thread.currentThread().getName();
		executor.submit(() -> {
			logger.setThreadName(name);
			runnable.run();
		});
	}

}
