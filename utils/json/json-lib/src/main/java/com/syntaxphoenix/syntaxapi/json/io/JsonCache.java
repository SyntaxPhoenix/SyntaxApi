package com.syntaxphoenix.syntaxapi.json.io;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

import com.syntaxphoenix.syntaxapi.json.JsonArray;

public final class JsonCache {
	
	public final Reader reader;
	
	public final JsonArray values = new JsonArray();
	
	public final char[] buffer = new char[4096];

	public final Stack<JsonToken> stack = new Stack<>();
	public JsonToken token;
	
	public int limit;
	public int cursor;
	
	public int lineAmount;
	public int lineCurrent;
	
	public JsonCache(Reader reader) throws IOException {
		this.reader = reader;
		reader.reset();
	}

}
