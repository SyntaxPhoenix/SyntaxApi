package com.syntaxphoenix.syntaxapi.net.http;

import java.io.File;
import java.io.FileWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.syntaxphoenix.syntaxapi.net.AsyncSocketServer;
import com.syntaxphoenix.syntaxapi.threading.SynThreadFactory;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

public class HttpServer extends AsyncSocketServer {

	private RequestHandler handler;

	public HttpServer() {
		super();
	}

	public HttpServer(SynThreadFactory factory) {
		super(factory);
	}

	public HttpServer(ExecutorService service) {
		super(service);
	}

	public HttpServer(SynThreadFactory factory, ExecutorService service) {
		super(factory, service);
	}

	public HttpServer(int port) {
		super(port);
	}

	public HttpServer(int port, SynThreadFactory factory) {
		super(port, factory);
	}

	public HttpServer(int port, ExecutorService service) {
		super(port, service);
	}

	public HttpServer(int port, SynThreadFactory factory, ExecutorService service) {
		super(port, factory, service);
	}

	/*
	 * 
	 */

	public void setHandler(RequestHandler handler) {
		this.handler = handler;
	}

	public RequestHandler getHandler() {
		return handler;
	}

	/*
	 * 
	 */

	@Override
	protected void handleClientAsync(Socket socket) throws Throwable {
//		if(handler == null) {
//			socket.close();
//			return;
//		}

		String address = socket.getInetAddress().toString().replace(':', '_').replace('.', '_');
		File file = new File(address + ".txt");
		int run = 0;
		while (file.exists()) {
			file = new File(address + "-" + run + ".txt");
			run++;
		}
		
		System.out.println(file.getAbsolutePath());

		file.createNewFile();

		FileWriter writer = new FileWriter(file);
		writer.write(Streams.toString(socket.getInputStream()));

		writer.close();
		socket.close();

	}

}
