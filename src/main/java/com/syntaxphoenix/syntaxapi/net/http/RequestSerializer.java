package com.syntaxphoenix.syntaxapi.net.http;

import com.google.gson.JsonObject;

@FunctionalInterface
public interface RequestSerializer {

	public JsonObject serialize(String raw);
	
}
