package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.json.JsonTools;

@FunctionalInterface
public interface ContentDeserializer {

	public static final ContentDeserializer JSON = value -> JsonTools.readJson(value);

	public static final ContentDeserializer URL_ENCODED = value -> {
		JsonObject output = new JsonObject();
		String[] entries = (value = value.replaceFirst("?", "")).contains("&") ? value.split("&")
				: new String[] { value };
		for (int index = 0; index < entries.length; index++) {
			String current = entries[index];
			if (!current.contains("="))
				continue;
			String[] entry = current.split("=", 2);
			output.addProperty(entry[0], entry[1]);
		}
		return output;
	};

	public static final ContentDeserializer PLAIN = value -> null;

	/*
	 * 
	 */

	public JsonObject process(String value);

}
