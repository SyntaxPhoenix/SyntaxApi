package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonDouble extends JsonNumber<Double> {

	public JsonDouble(Double value) {
		super(value);
	}

	@Override
	public final ValueType getType() {
		return ValueType.DOUBLE;
	}

}
