package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;

public class JsonNull<E> extends JsonValue<E> {

	private static JsonNull<?> INSTANCE = new JsonNull<>();

	@SuppressWarnings("unchecked")
	public static <E> JsonNull<E> get() {
		return (JsonNull<E>) INSTANCE;
	}

	private JsonNull() {
	}

	@Override
	public ValueType getType() {
		return ValueType.NULL;
	}

	@Override
	public E getValue() {
		return null;
	}

}
