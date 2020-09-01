package com.syntaxphoenix.syntaxapi.net.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;

import com.google.gson.JsonElement;

public class DefaultSerializers {

	/*
	 * Default serializers
	 */

	public static final ContentSerializer PLAIN = null;

	/*
	 * Json serializers
	 */

	public static final JsonContentSerializer JSON = parameters -> parameters.toString();

	public static final JsonContentSerializer URL_ENCODED = parameters -> {
		if (parameters.entrySet().isEmpty())
			return "";

		StringBuilder builder = new StringBuilder();

		try {
			for (Entry<String, JsonElement> parameter : parameters.entrySet()) {
				builder.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(parameter.getValue().getAsString(), "UTF-8"));
				builder.append('&');
			}
		} catch (UnsupportedEncodingException ignore) {
		}

		String value = builder.toString();
		return value.substring(0, value.length() - 1);
	};

}
