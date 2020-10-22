package com.syntaxphoenix.syntaxapi.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.json.utils.SynchronizedIterator;

public class JsonArray extends JsonValue<List<JsonValue<?>>> implements Iterable<JsonValue<?>> {

	private final ArrayList<JsonValue<?>> values = new ArrayList<>();

	@Override
	public final ValueType getType() {
		return ValueType.ARRAY;
	}

	@Override
	public List<JsonValue<?>> getValue() {
		return values;
	}

	public JsonArray add(JsonValue<?> value) {
		if (value == null)
			return this;
		synchronized (values) {
			values.add(value);
		}
		return this;
	}

	public JsonArray add(Object value) {
		return add(JsonValue.fromPrimitive(value));
	}

	public Optional<JsonValue<?>> remove(int index) {
		if (index >= size() || index < 0)
			return Optional.empty();
		synchronized (values) {
			return Optional.ofNullable(values.remove(index));
		}
	}

	public boolean remove(JsonValue<?> value) {
		synchronized (values) {
			return values.remove(value);
		}
	}

	public JsonValue<?> get(int index) {
		return optional(index).orElse(null);
	}

	public Optional<JsonValue<?>> optional(int index) {
		if (index >= size() || index < 0)
			return Optional.empty();
		synchronized (values) {
			return Optional.ofNullable(values.get(index));
		}
	}

	public boolean has(JsonValue<?> value) {
		synchronized (values) {
			return values.contains(value);
		}
	}

	public boolean has(int index) {
		return optional(index).isPresent();
	}

	public boolean has(int index, ValueType type) {
		return optional(index).filter(value -> value.hasType(type)).isPresent();
	}

	public int indexOf(JsonValue<?> value) {
		synchronized (values) {
			return values.indexOf(value);
		}
	}

	public int size() {
		synchronized (values) {
			return values.size();
		}
	}

	@Override
	public SynchronizedIterator<JsonValue<?>> iterator() {
		return new SynchronizedIterator<>(values);
	}

	public JsonValue<?>[] toArray() {
		synchronized (values) {
			return values.toArray(new JsonValue<?>[0]);
		}
	}

}
