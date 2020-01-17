package com.syntaxphoenix.syntaxapi.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonTools {
	
	private static JsonParser parser = new JsonParser();
	private static Gson gson;
	
	public static Gson getConfiguredGson() {
		if(gson != null) {
			return gson;
		}
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		return gson = builder.create();
	}
	
	public static JsonParser getJsonParser() {
		return parser;
	}
	/**
	 * @param String {input json}
	 * @return JsonObject {output json}
	 */
	public static JsonObject readJson(String input) {
		return getJsonParser().parse(input).getAsJsonObject();
	}

}
