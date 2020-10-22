package com.syntaxphoenix.syntaxapi.json.value;

import com.syntaxphoenix.syntaxapi.json.JsonValue;

public abstract class JsonSimple<E> extends JsonValue<E> {

	protected final E value;

	public JsonSimple(E value) {
		this.value = value;
	}

	@Override
	public E getValue() {
		return value;
	}

}
