package com.syntaxphoenix.syntaxapi.logging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import com.syntaxphoenix.syntaxapi.logging.color.LogType;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;

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
	
	@Override
	public void close() {
		logger.close();
	}
	
	/*
	 * 
	 */

	@Override
	public AsyncLogger setThreadName(String name) {
		logger.setThreadName(name);
		return this;
	}

	@Override
	public String getThreadName() {
		return logger.getThreadName();
	}
	
	@Override
	public AsyncLogger setState(LoggerState state) {
		logger.setState(state);
		return this;
	}

	@Override
	public LoggerState getState() {
		return logger.getState();
	}

	@Override
	public AsyncLogger setCustom(BiConsumer<Boolean, String> custom) {
		logger.setCustom(custom);
		return this;
	}

	@Override
	public BiConsumer<Boolean, String> getCustom() {
		return logger.getCustom();
	}

	@Override
	public AsyncLogger setType(LogType type) {
		logger.setType(type);
		return this;
	}

	@Override
	public LogType getType(String typeId) {
		return logger.getType(typeId);
	}
	
	@Override
	public ILogger setColored(boolean color) {
		logger.setColored(color);
		return this;
	}
	
	@Override
	public boolean isColored() {
		return logger.isColored();
	}
	
	@Override
	public LogTypeMap getTypeMap() {
		return logger.getTypeMap();
	}
	
	/*
	 * 
	 */

	public void log(String message) {
		queue(() -> logger.log(message));
	}

	public void log(LogTypeId type, String message) {
		queue(() -> logger.log(type, message));
	}

	public void log(String typeId, String message) {
		queue(() -> logger.log(typeId, message));
	}

	public void log(String... messages) {
		queue(() -> logger.log(messages));
	}

	public void log(LogTypeId type, String... messages) {
		queue(() -> logger.log(type, messages));
	}

	public void log(String typeId, String... messages) {
		queue(() -> logger.log(typeId, messages));
	}

	public void log(Throwable throwable) {
		queue(() -> logger.log(throwable));
	}

	public void log(LogTypeId type, Throwable throwable) {
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
