package com.syntaxphoenix.syntaxapi.threading;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class SynThreadPool implements ExecutorService {

	private final SynThreadReporter reporter;
	private final SynThreadFactory factory;
	private final ExecutorService service;
	
	public SynThreadPool(SynThreadReporter reporter, int min, int max, String poolName) {
		if(min <= 0) {
			throw new IllegalArgumentException("Minimum thread count cannot be lower than 1");
		} else if(max < min || max <= 0) {
			throw new IllegalArgumentException("Maximum thread count need to be higher than 0 and higher than or equal to the minimum thread count!");
		}
		this.reporter = reporter;
		this.service = new ThreadPoolExecutor(min, max, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), (this.factory = new SynThreadFactory(poolName)));
	}
	
	public final SynThreadFactory getThreadFactory() {
		return factory;
	}
	
	public final String getName() {
		return factory.getName();
	}
	
	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public void execute(Runnable command) {
		service.execute(() -> {
			try {
				command.run();
			} catch(Throwable throwable) {
				reporter.catchFail(throwable, this, Thread.currentThread(), command);
			}
		});
	}

	@Override
	public void shutdown() {
		service.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return service.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return service.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return service.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return service.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return service.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return service.submit(task, result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return service.submit(task);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return service.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		return service.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return service.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return service.invokeAny(tasks, timeout, unit);
	}
	
}
