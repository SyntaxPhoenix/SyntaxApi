package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonShort extends JsonNumber<Short> {

	public JsonShort(Short value) {
		super(value);
	}

	@Override
	public final ValueType getType() {
		return ValueType.SHORT;
	}

}
