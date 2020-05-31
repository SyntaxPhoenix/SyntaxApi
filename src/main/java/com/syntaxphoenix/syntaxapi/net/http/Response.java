package com.syntaxphoenix.syntaxapi.net.http;

public class Response {
	
	private final int responseCode;
	private final String response;
	
	public Response(int responseCode, String response) {
		this.responseCode = responseCode;
		this.response = response;
	}
	
	/*
	 * 
	 */
	
	public int getCode() {
		return responseCode;
	}
	
	public String getResponse() {
		return response; 
	}

}
