package com.syntaxphoenix.syntaxapi.json.io;

public enum JsonState {

	EOF(JsonToken.EOF),
	NULL(JsonToken.NULL),
	TRUE(JsonToken.BOOLEAN),
	FALSE(JsonToken.BOOLEAN),
	KEY_SINGLE(JsonToken.KEY),
	KEY_DOUBLE(JsonToken.KEY),
	VALUE_SINGLE(JsonToken.STRING),
	VALUE_DOUBLE(JsonToken.STRING),
	END_ARRAY(JsonToken.END_ARRAY),
	END_OBJECT(JsonToken.END_OBJECT),
	START_ARRAY(JsonToken.START_ARRAY),
	START_OBJECT(JsonToken.START_OBJECT),

	// Numbers
	BYTE(JsonToken.BYTE),
	SHORT(JsonToken.SHORT),
	INTEGER(JsonToken.INTEGER),
	LONG(JsonToken.LONG),
	BIG_INTEGER(JsonToken.BIG_INTEGER),
	FLOAT(JsonToken.FLOAT),
	DOUBLE(JsonToken.DOUBLE),
	BIG_DECIMAL(JsonToken.BIG_DECIMAL);
	
	private final JsonToken token;

	private JsonState(JsonToken token) {
		this.token = token;
	}

	public JsonToken asToken() {
		return token;
	}

}
