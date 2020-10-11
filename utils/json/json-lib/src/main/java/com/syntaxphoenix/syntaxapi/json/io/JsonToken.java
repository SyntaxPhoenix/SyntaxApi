package com.syntaxphoenix.syntaxapi.json.io;

public enum JsonToken {
	
	EOF(-1),
	KEY(0),
	VALUE(1),
	SEPERATOR(2),
	START_OBJECT(3),
	END_OBJECT(4),
	START_ARRAY(5),
	END_ARRAY(6);

	private final int id;

	private JsonToken(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

}
