package com.syntaxphoenix.syntaxapi.net.http;

import java.net.Socket;

@FunctionalInterface
public interface RequestHandler {
	
	public boolean handleRequest(Socket client, HttpWriter writer, ReceivedRequest request);

}
