package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonBoolean extends JsonSimple<Boolean> {

	public JsonBoolean(Boolean value) {
		super(value);
	}

	@Override
	public ValueType getType() {
		return ValueType.BOOLEAN;
	}

}
