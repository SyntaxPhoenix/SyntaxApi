package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonAnswer extends Answer<JsonObject> {

	private JsonObject object = new JsonObject();

	/*
	 * 
	 */

	public JsonAnswer(ContentType type) {
		super(type);
	}

	/*
	 * 
	 */

	public JsonElement respond(String key) {
		return object.get(key);
	}

	/*
	 * 
	 */

	public JsonAnswer code(int code) {
		code(code);
		return this;
	}

	public JsonAnswer header(String key, Object value) {
		header(key, value);
		return this;
	}

	public JsonAnswer header(String key, String value) {
		header(key, value);
		return this;
	}

	public JsonAnswer respond(String key, String value) {
		if (value != null)
			object.addProperty(key, value);
		else
			object.remove(key);
		return this;
	}

	public JsonAnswer respond(String key, JsonElement element) {
		if (element != null)
			object.add(key, element);
		else
			object.remove(key);
		return this;
	}

	public JsonAnswer respond(JsonObject object) {
		this.object = object;
		return this;
	}

	/*
	 * 
	 */

	public JsonAnswer clearHeaders() {
		clearHeaders();
		return this;
	}

	public JsonAnswer clearResponse() {
		object = new JsonObject();
		return this;
	}

	/*
	 * 
	 */

	public boolean hasResponse() {
		return !object.keySet().isEmpty();
	}

	@Override
	public JsonObject getResponse() {
		return object;
	}

	@Override
	public byte[] serializeResponse() {
		return serializeString(object.toString());
	}

}
