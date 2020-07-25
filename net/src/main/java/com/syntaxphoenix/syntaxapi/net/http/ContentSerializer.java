package com.syntaxphoenix.syntaxapi.net.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
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
				builder.append(URLEncoder.encode(parameter.getValue().getAsString(), "UTF-8"));
				builder.append('&');
			}
		} catch (UnsupportedEncodingException ignore) {
		}

		String value = builder.toString();
		return value.substring(0, value.length() - 1);
	};

	public static final ContentSerializer PLAIN = new ContentSerializer() {

		private final char[] CHARS = new char[] { ':', ' ', '\t', '\n', '-' };
		private final String NULL = "null";
		private final String NEXT = "==>";

		public String process(JsonObject parameters) {
			StringBuilder builder = new StringBuilder();

			append(builder, parameters, 0);

			return builder.toString();
		}

		private boolean append(StringBuilder builder, JsonObject object, int depth) {

			for (Entry<String, JsonElement> entry : object.entrySet()) {

				JsonElement element = entry.getValue();

				for (int current = 0; current < depth; current++)
					builder.append(CHARS[2]);

				builder.append(entry.getKey());
				builder.append(CHARS[0]);
				builder.append(CHARS[1]);

				if (element == null || element.isJsonNull()) {
					builder.append(NULL);
					continue;
				}

				if (element.isJsonPrimitive()) {
					builder.append(element.getAsString());
					continue;
				}

				builder.append(CHARS[3]);

				if (element.isJsonArray()) {
					append(builder, element.getAsJsonArray(), depth + 1);
					continue;
				}

				append(builder, element.getAsJsonObject(), depth + 1);

			}

			return true;
		}

		private boolean append(StringBuilder builder, JsonArray array, int depth) {

			for (JsonElement element : array) {

				for (int current = 0; current < depth; current++)
					builder.append(CHARS[2]);

				builder.append(CHARS[4]);
				builder.append(CHARS[0]);

				if (element == null || element.isJsonNull()) {
					builder.append(NULL);
					continue;
				}

				if (element.isJsonPrimitive()) {
					builder.append(element.getAsString());
					continue;
				}

				builder.append(NEXT);
				builder.append(CHARS[3]);

				if (element.isJsonArray()) {
					append(builder, element.getAsJsonArray(), depth + 1);
					continue;
				}

				append(builder, element.getAsJsonObject(), depth + 1);

			}

			return true;
		}

	};

	/*
	 * 
	 */

	public String process(JsonObject parameters);

}
