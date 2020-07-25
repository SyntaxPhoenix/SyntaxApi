package com.syntaxphoenix.syntaxapi.net.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;

public class Response {

	private final JsonObject headers;
	private final int responseCode;
	private final byte[] response;

	public Response(int responseCode, byte[] response, Map<String, List<String>> headerMap) {
		this.responseCode = responseCode;
		this.response = response;
		this.headers = headerMap.entrySet().stream().collect(JsonTools.toJsonObject());
	}

	/*
	 * Header management
	 */

	public boolean has(String header) {
		return headers.has(header.toLowerCase());
	}

	public boolean hasMultiple(String header) {
		if (!has(header))
			return false;
		return get(header).isJsonArray();
	}

	public JsonElement get(String header) {
		return headers.get(header);
	}

	public JsonArray getMultiple(String header) {
		return headers.get(header).getAsJsonArray();
	}

	public String getAsString(String header) {
		return get(header).getAsString();
	}

	/*
	 * Getters
	 */

	public int getCode() {
		return responseCode;
	}

	public byte[] getResponseBytes() {
		return response;
	}

	public String getResponse() {
		try {
			return Streams.toString(new ByteArrayInputStream(response));
		} catch (IOException e) {
			return new String(response);
		}
	}

	public JsonObject getResponseAsJson() {
		return JsonTools.readJson(getResponse());
	}

	public JsonObject getResponseHeaders() {
		return headers;
	}

}
