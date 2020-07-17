package com.syntaxphoenix.syntaxapi.net.http;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.json.ValueIdentifier;

public class ReceivedRequest {

	private final HashMap<String, Object> headers = new HashMap<>();

	private final RequestType type;
	private final String[] path;

	private JsonObject data = new JsonObject();

	public ReceivedRequest(RequestType type, String... path) {
		this.type = type;
		this.path = path;
	}

	/*
	 * 
	 */

	public final ReceivedRequest parseHeaders(Iterable<String> headers) {
		return parseHeaders(headers.iterator());
	}

	public final ReceivedRequest parseHeaders(Iterator<String> headers) {
		while (headers.hasNext())
			parseHeader(headers.next());
		return this;
	}

	public final ReceivedRequest parseHeaders(String... headers) {
		if (headers == null || headers.length == 0)
			return this;
		for (String header : headers)
			parseHeader(header);
		return this;
	}

	public final ReceivedRequest parseHeader(String header) {
		if (!header.contains(":"))
			return this;
		String[] parts = header.split(":", 2);
		headers.put(parts[0].toLowerCase(), ValueIdentifier.identify(parts[1].trim()));
		return this;
	}

	/*
	 * 
	 */

	public final HashMap<String, Object> getHeaders() {
		return headers;
	}

	public final Object getHeader(String key) {
		return headers.get(key.toLowerCase());
	}

	public final boolean hasHeader(String key) {
		return headers.containsKey(key.toLowerCase());
	}
	
	/*
	 * 
	 */
	
	public final ReceivedRequest setData(JsonObject data) {
		this.data = data;
		return this;
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

	public final String[] getPath() {
		return path;
	}

}
