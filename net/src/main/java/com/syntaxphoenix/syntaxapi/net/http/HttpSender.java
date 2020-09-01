package com.syntaxphoenix.syntaxapi.net.http;

import java.io.BufferedReader;
import java.net.Socket;

public class HttpSender {

	private final Socket client;
	private final BufferedReader input;

	public HttpSender(Socket client, BufferedReader input) {
		this.client = client;
		this.input = input;
	}

	public Socket getClient() {
		return client;
	}

	public BufferedReader getInput() {
		return input;
	}

}
