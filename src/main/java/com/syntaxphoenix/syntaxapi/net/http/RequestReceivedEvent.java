package com.syntaxphoenix.syntaxapi.net.http;

import java.net.Socket;

import com.syntaxphoenix.syntaxapi.event.Event;

public class RequestReceivedEvent extends Event {
	
	private final ReceivedRequest request;
	private final HttpWriter writer;
	private final Socket client;
	
	public RequestReceivedEvent(Socket client, HttpWriter writer, ReceivedRequest request) {
		this.client = client;
		this.writer = writer;
		this.request = request;
	}
	
	/*
	 * 
	 */
	
	public Socket getClient() {
		return client;
	}
	
	public HttpWriter getWriter() {
		return writer;
	}
	
	public ReceivedRequest getRequest() {
		return request;
	}

}
