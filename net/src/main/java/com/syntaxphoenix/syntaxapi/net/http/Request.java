package com.syntaxphoenix.syntaxapi.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.syntaxphoenix.syntaxapi.utils.java.Streams;

public class Request {

	private final HashMap<String, String> headers = new HashMap<>();
	private final RequestType request;

	private JsonObject parameters = new JsonObject();

	/*
	 * 
	 */

	public Request(RequestType request) {
		this.request = request;
	}

	/*
	 * 
	 */

	public Request header(String key, String value) {
		if (value != null)
			headers.put(key, value);
		else
			headers.remove(key);
		return this;
	}

	public Request parameter(String key, String value) {
		if (value != null)
			parameters.addProperty(key, value);
		else
			parameters.remove(key);
		return this;
	}

	public Request parameter(String key, JsonElement element) {
		if (element != null)
			parameters.add(key, element);
		else
			parameters.remove(key);
		return this;
	}

	public Request parameter(JsonObject object) {
		parameters = object;
		return this;
	}

	/*
	 * 
	 */

	public Request clearHeader() {
		headers.clear();
		return this;
	}

	public Request clearParameters() {
		parameters = new JsonObject();
		return this;
	}

	/*
	 * 
	 */

	public boolean hasParameters() {
		return !parameters.keySet().isEmpty();
	}

	/*
	 * 
	 */

	public Response execute(String url, ContentType content) throws IOException {
		return execute(new URL(url), content);
	}

	public Response execute(URL url, ContentType content) throws IOException {

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod(request.name());

		byte[] data = null;
		int length = 0;

		if (hasParameters()) {

			data = content.serialize(parameters).getBytes(StandardCharsets.UTF_8);
			length = data.length;

			connection.setDoOutput(true);

		}

		connection.setFixedLengthStreamingMode(length);

		for (Entry<String, String> header : headers.entrySet()) {
			connection.setRequestProperty(header.getKey(), header.getValue());
		}

		if (content != ContentType.CUSTOM)
			connection.setRequestProperty("Content-Type", content.type() + "; charset=UTF-8");

		connection.connect();

		if (connection.getDoOutput()) {

			OutputStream output = connection.getOutputStream();
			output.write(data);
			output.flush();
			output.close();

		}

		InputStream stream = null;

		try {
			stream = connection.getInputStream();
		} catch (IOException ignore) {
			stream = connection.getErrorStream();
		}

		byte[] response = new byte[0];

		if (stream != null)
			response = Streams.toByteArray(stream);

		return new Response(connection.getResponseCode(), response, connection.getHeaderFields());

	}

}
