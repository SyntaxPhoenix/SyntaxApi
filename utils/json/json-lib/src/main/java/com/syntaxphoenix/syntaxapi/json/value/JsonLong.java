package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonLong extends JsonNumber<Long> {
	
	public JsonLong(Long value) {
		super(value);
	}

	@Override
	public final ValueType getType() {
		return ValueType.LONG;
	}
	
}
