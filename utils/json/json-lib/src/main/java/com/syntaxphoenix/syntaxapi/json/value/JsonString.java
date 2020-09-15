package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonString extends JsonSimple<String> {
	
	public JsonString(String value) {
		super(value);
	}

	@Override
	public final ValueType getType() {
		return ValueType.STRING;
	}

}
