package com.syntaxphoenix.syntaxapi.net.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.java.Lists;

public class Answer {
	
	public static final Answer CONTINUE = new Answer(ContentType.JSON).code(ResponseCode.CONTINUE);
	public static final Answer NO_CONTENT = new Answer(ContentType.JSON).code(ResponseCode.NO_CONTENT);
	public static final Answer SERVER_ERROR = new Answer(ContentType.JSON).code(ResponseCode.INTERNAL_SERVER_ERROR);
	
	/*
	 * 
	 */
	
	public static final List<String> BLOCKED = Lists.asList("Content-Type", "Server", "Date", "Content-Length");
	
	/*
	 * 
	 */

	private final HashMap<String, String> headers = new HashMap<>();
	private final ContentType type;
	
	private JsonObject object = new JsonObject();
	private int code = 404;

	/*
	 * 
	 */

	public Answer(ContentType type) {
		this.type = type;
	}
	
	/*
	 * 
	 */
	
	public String header(String key) {
		return headers.get(key);
	}
	
	public JsonElement respond(String key) {
		return object.get(key);
	}
	
	public ContentType type() {
		return type;
	}
	
	public int code() {
		return code;
	}

	/*
	 * 
	 */
	
	public Answer code(int code) {
		this.code = code;
		return this;
	}

	public Answer header(String key, Object value) {
		return header(key, value.toString());
	}

	public Answer header(String key, String value) {
		if (value != null)
			headers.put(key, value);
		else
			headers.remove(key);
		return this;
	}

	public Answer respond(String key, String value) {
		if (value != null)
			object.addProperty(key, value);
		else
			object.remove(key);
		return this;
	}

	public Answer respond(String key, JsonElement element) {
		if (element != null)
			object.add(key, element);
		else
			object.remove(key);
		return this;
	}

	public Answer respond(JsonObject object) {
		this.object = object;
		return this;
	}

	/*
	 * 
	 */

	public Answer clearHeaders() {
		headers.clear();
		return this;
	}

	public Answer clearResponse() {
		object = new JsonObject();
		return this;
	}

	/*
	 * 
	 */

	public boolean hasResponse() {
		return !object.keySet().isEmpty();
	}

	/*
	 * 
	 */

	public Answer write(HttpWriter writer) throws IOException {
		if(type == null)
			return this;
			
		byte[] data = null;
		int length = 0;

		if (object != null && hasResponse()) {
			
			data = type.serialize(object).getBytes(StandardCharsets.UTF_8);
			length = data.length;
			
		}
		
		writer.write(code).writeServer().writeDate().writeLength(length);
		

		for (Entry<String, String> header : headers.entrySet()) {
			if(BLOCKED.contains(header.getKey()))
				continue;
			writer.write(header);
		}

		if(type == ContentType.CUSTOM)
			writer.writeType(headers.get("Content-Type"));
		else
			writer.writeType(type);
		
		writer.line();
		
		if(data != null)
			writer.write(data);
		
		writer.clear();
		
		return this;
	}

}
