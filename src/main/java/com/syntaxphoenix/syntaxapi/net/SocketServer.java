package com.syntaxphoenix.syntaxapi.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.syntaxphoenix.syntaxapi.threading.SynThreadFactory;
import com.syntaxphoenix.syntaxapi.utils.java.Keys;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class SocketServer {

	public static final int DEFAULT_PORT = 80;

	/*
	 * 
	 */

	private final ExecutorService serverThread;
	private final int port;

	private ServerSocket serverSocket;

	public SocketServer() {
		this(DEFAULT_PORT);
	}

	public SocketServer(SynThreadFactory factory) {
		this(DEFAULT_PORT, factory);
	}

	public SocketServer(int port) {
		this(port, new SynThreadFactory(Keys.generateKey(8)));
	}

	public SocketServer(int port, SynThreadFactory factory) {
		this.port = port;
		this.serverThread = Executors.newSingleThreadExecutor(factory);
	}
	
	/*
	 * 
	 */
	
	public final void applyName(String name) {
		this.serverThread.submit(() -> Thread.currentThread().setName(name));
	}

	/*
	 * 
	 */

	public final ExecutorService getServerThread() {
		return serverThread;
	}

	public final int getPort() {
		return port;
	}

	public final ServerSocket getSocket() {
		return serverSocket;
	}

	/*
	 * 
	 */

	public void start() throws IOException {
		if (serverSocket != null)
			return;

		serverSocket = new ServerSocket(port);
		serverThread.submit(() -> {
			try {
				handleServer();
			} catch (Throwable throwable) {
				handleException(throwable);
			}
		});
	}

	public void stop() throws IOException {
		if (serverSocket == null)
			return;

		serverSocket.close();
		serverSocket = null;
	}

	public boolean isStarted() {
		return serverSocket != null;
	}

	/*
	 * 
	 */

	protected void handleException(Throwable throwable) {
		throwable.printStackTrace();
	}

	protected void handleServer() {

		while (serverSocket != null) {
			
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			} catch (Throwable throwable) {
				handleException(throwable);
			}
			
			if (clientSocket == null)
				continue;
			boolean open = true;
			try {
				handleClient(clientSocket);
			} catch (Throwable throwable) {
				handleException(throwable);
				open = false;
			}
			
			if (open)
				continue;
			try {
				clientSocket.close();
			} catch (Throwable throwable) {
				handleException(throwable);
			}
			
		}

	}

	protected void handleClient(Socket socket) throws Throwable {
		throw new NotImplementedException();
	}

}
