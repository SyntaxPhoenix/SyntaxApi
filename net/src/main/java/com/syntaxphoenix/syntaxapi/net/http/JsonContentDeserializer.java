package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;

public interface JsonContentDeserializer extends ContentDeserializer {

	@Override
	default RequestData<?> process(String value) {
		return new CustomRequestData<>(JsonObject.class, parse(value));
	}

	public JsonObject parse(String value);

}
