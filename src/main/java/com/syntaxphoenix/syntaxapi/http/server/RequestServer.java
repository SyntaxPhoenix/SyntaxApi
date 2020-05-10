package com.syntaxphoenix.syntaxapi.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

public class RequestServer {

	private HttpServer server;
	private boolean valid = false;

	public RequestServer(int port) {
		this(port, null);
	}

	public RequestServer(int port, ILogger logger) {
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			if (logger != null) {
				logger.log(e);
				return;
			}
			e.printStackTrace();
		}
		valid = true;
	}
	
	/*
	 * 
	 */
	
	public boolean isValid() {
		return valid;
	}
	
	public HttpServer getHttpServer() {
		return server;
	}

}
