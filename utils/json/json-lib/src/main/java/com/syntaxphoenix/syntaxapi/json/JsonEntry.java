package com.syntaxphoenix.syntaxapi.json;

import java.util.Objects;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.json.value.JsonNull;

public class JsonEntry<E extends JsonValue<?>> {

	private final String key;
	private final E value;

	@SuppressWarnings("unchecked")
	public JsonEntry(String key, E value) {
		this.key = Objects.requireNonNull(key);
		this.value = value == null ? (E) JsonNull.get() : value;
	}

	public String getKey() {
		return key;
	}

	public ValueType getType() {
		return value == null ? ValueType.NULL : value.getType();
	}

	public E getValue() {
		return value;
	}

	public Optional<E> getOptional() {
		return Optional.ofNullable(value);
	}

}
