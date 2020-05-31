package com.syntaxphoenix.syntaxapi.net;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.syntaxphoenix.syntaxapi.threading.SynThreadFactory;
import com.syntaxphoenix.syntaxapi.utils.java.Keys;

public abstract class AsyncSocketServer extends SocketServer {

	private final ExecutorService service;

	public AsyncSocketServer() {
		this(new SynThreadFactory(Keys.generateKey(8)));
	}

	public AsyncSocketServer(SynThreadFactory factory) {
		this(factory, Executors.newCachedThreadPool(factory));
	}

	public AsyncSocketServer(ExecutorService service) {
		this(DEFAULT_PORT, service);
	}

	public AsyncSocketServer(SynThreadFactory factory, ExecutorService service) {
		this(DEFAULT_PORT, factory, service);
	}

	public AsyncSocketServer(int port) {
		this(port, new SynThreadFactory(Keys.generateKey(8)));
	}

	public AsyncSocketServer(int port, SynThreadFactory factory) {
		this(port, factory, Executors.newCachedThreadPool(factory));
	}

	public AsyncSocketServer(int port, ExecutorService service) {
		super(port);
		this.service = service;
	}

	public AsyncSocketServer(int port, SynThreadFactory factory, ExecutorService service) {
		super(port, factory);
		this.service = service;
	}

	/*
	 * 
	 */

	public final ExecutorService getExecutorSerivce() {
		return service;
	}

	/*
	 * 
	 */

	@Override
	protected void handleClient(Socket socket) throws Throwable {
		service.submit(() -> {
			try {
				handleClientAsync(socket);
			} catch (Throwable throwable) {
				handleExceptionAsync(throwable);
			}
		});
	}

	protected void handleExceptionAsync(Throwable throwable) {
		throwable.printStackTrace();
	}

	protected abstract void handleClientAsync(Socket socket) throws Throwable;

}
