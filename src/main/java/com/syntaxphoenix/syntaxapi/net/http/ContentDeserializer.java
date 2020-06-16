package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.config.JsonTools;

@FunctionalInterface
public interface ContentDeserializer {

	public static final ContentDeserializer JSON = value -> JsonTools.readJson(value);
	
	public static final ContentDeserializer URL_ENCODED = value -> {
		return null;
	};

	/*
	 * 
	 */

	public JsonObject process(String value);

}
