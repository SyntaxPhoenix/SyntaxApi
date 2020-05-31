package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;

public class ReceivedRequest {
	
	private final RequestType type;
	private final JsonObject data;
	
	public ReceivedRequest(RequestType type, JsonObject data) {
		this.type = type;
		this.data = data;
	}
	
	/*
	 * 
	 */
	
	public final RequestType getType() {
		return type;
	}
	
	public final JsonObject getData() {
		return data;
	}

}
