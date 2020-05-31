package com.syntaxphoenix.syntaxapi.net.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

@FunctionalInterface
public interface ContentProcessor {

	public static final ContentProcessor JSON = parameters -> {
		JsonObject object = new JsonObject();

		if (!parameters.isEmpty())
			for (Entry<String, String> parameter : parameters.entrySet())
				object.addProperty(parameter.getKey(), parameter.getValue());

		return object.toString();
	};
	
	public static final ContentProcessor URL_ENCODED = parameters -> {
		if (parameters.isEmpty())
			return "";

		StringBuilder builder = new StringBuilder();

		try {
			for (Entry<String, String> parameter : parameters.entrySet()) {
				builder.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(parameter.getValue());
				builder.append('&');
			}
		} catch (UnsupportedEncodingException ignore) {
		}

		String value = builder.toString();
		return value.substring(0, value.length() - 1);
	};

	/*
	 * 
	 */

	public String process(Map<String, String> parameters);

}
