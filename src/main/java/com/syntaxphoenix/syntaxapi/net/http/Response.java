package com.syntaxphoenix.syntaxapi.net.http;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.config.JsonTools;

public class Response {

	private final JsonObject headers;
	private final int responseCode;
	private final String response;

	public Response(int responseCode, String response, Map<String, List<String>> headerMap) {
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

	public String getResponse() {
		return response;
	}

	public JsonObject getResponseAsJson() {
		return JsonTools.readJson(response);
	}

	public JsonObject getResponseHeaders() {
		return headers;
	}

}
