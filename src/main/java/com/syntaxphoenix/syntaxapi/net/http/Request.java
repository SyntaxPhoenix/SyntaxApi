package com.syntaxphoenix.syntaxapi.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.utils.java.Streams;

public class Request {
	
	private final HashMap<String, String> headers = new HashMap<>();
	private final HashMap<String, String> parameters = new HashMap<>();
	
	private final RequestType request;
	
	/*
	 * 
	 */
	
	public Request(RequestType request) {
		this.request = request;
	}
	
	/*
	 * 
	 */
	
	public Request header(String key, String value) {
		if(value != null)
			headers.put(key, value);
		else
			headers.remove(key);
		return this;
	}
	
	public Request parameter(String key, String value) {
		if(value != null)
			parameters.put(key, value);
		else
			parameters.remove(key);
		return this;
	}
	
	/*
	 * 
	 */
	
	public Request clearHeader() {
		headers.clear();
		return this;
	}
	
	public Request clearParameters() {
		parameters.clear();
		return this;
	}
	
	/*
	 * 
	 */
	
	public Response execute(String url, ContentType content) throws IOException {
		return execute(new URL(url), content);
	}
	
	public Response execute(URL url, ContentType content) throws IOException {
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod(request.name());
		connection.setDoOutput(true);
		
		byte[] data = content.process(parameters).getBytes(StandardCharsets.UTF_8);
		
		connection.setFixedLengthStreamingMode(data.length);
		connection.setRequestProperty("Content-Type", content.type() + "; charset=UTF-8");
		connection.connect();
		
		OutputStream output = connection.getOutputStream();
		output.write(data);
		output.flush();
		output.close();
		
		InputStream input = connection.getInputStream();
		
		String response = "";
		
		if(input != null)
			response = Streams.toString(input);
		
		return new Response(connection.getResponseCode(), response);
		
	}

}
