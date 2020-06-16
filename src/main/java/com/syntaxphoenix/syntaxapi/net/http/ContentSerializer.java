package com.syntaxphoenix.syntaxapi.net.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@FunctionalInterface
public interface ContentSerializer {

	public static final ContentSerializer JSON = parameters -> parameters.toString();
	
	public static final ContentSerializer URL_ENCODED = parameters -> {
		if (parameters.entrySet().isEmpty())
			return "";
		
		StringBuilder builder = new StringBuilder('?');

		try {
			for (Entry<String, JsonElement> parameter : parameters.entrySet()) {
				builder.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(parameter.getValue().toString(), "UTF-8"));
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

	public String process(JsonObject parameters);

}
