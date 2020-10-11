package com.syntaxphoenix.syntaxapi.json.io;

import java.io.IOException;
import java.io.Reader;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;
import com.syntaxphoenix.syntaxapi.utils.io.TextDeserializer;

public class JsonParser implements TextDeserializer<JsonValue<?>> {

	public static final int CHARACTER_START_OBJECT = '{';
	public static final int CHARACTER_END_OBJECT = '}';
	public static final int CHARACTER_START_ARRAY = '[';
	public static final int CHARACTER_END_ARRAY = ']';
	public static final int CHARACTER_SEPERATOR = ',';
	public static final int CHARACTER_VALUE = '\"';
	public static final int CHARACTER_KEY = ':';

	public static final int TOKEN_EOF = -1;
	public static final int TOKEN_KEY = 0;
	public static final int TOKEN_VALUE = 1;
	public static final int TOKEN_SEPERATOR = 2;
	public static final int TOKEN_START_OBJECT = 3;
	public static final int TOKEN_END_OBJECT = 4;
	public static final int TOKEN_START_ARRAY = 5;
	public static final int TOKEN_END_ARRAY = 6;

	public static final int INDICATOR_EOL = 0;
	public static final int INDICATOR_EOC = 1;

	@Override
	public JsonValue<?> fromReader(Reader reader) throws IOException {
		JsonReader jasonReader = new JsonReader(reader);
		JsonArray output = jasonReader.parse();
		switch (output.size()) {
		case 0:
			return JsonNull.get();
		case 1:
			return output.get(0);
		default:
			return output;
		}
	}

}
