package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonByte extends JsonNumber<Byte> {

	public JsonByte(Byte value) {
		super(value);
	}

	@Override
	public ValueType getType() {
		return ValueType.BYTE;
	}

}
