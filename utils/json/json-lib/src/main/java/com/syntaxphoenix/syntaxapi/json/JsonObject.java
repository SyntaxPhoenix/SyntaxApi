package com.syntaxphoenix.syntaxapi.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.json.utils.SynchronizedIterator;

public class JsonObject extends JsonValue<Map<String, JsonValue<?>>> implements Iterable<JsonEntry<?>> {

	private final ArrayList<JsonEntry<?>> values = new ArrayList<>();

	@Override
	public final ValueType getType() {
		return ValueType.OBJECT;
	}

	@Override
	public Map<String, JsonValue<?>> getValue() {
		HashMap<String, JsonValue<?>> values = new HashMap<>();
		synchronized (this.values) {
			for (JsonEntry<?> entry : this.values) {
				values.put(entry.getKey(), entry.getValue());
			}
		}
		return values;
	}

	public JsonObject set(String key, JsonValue<?> value) {
		if (value == null)
			return this;
		remove(key);
		synchronized (values) {
			values.add(new JsonEntry<>(key, value));
		}
		return this;
	}

	public JsonObject set(String key, Object value) {
		return set(key, JsonValue.fromPrimitive(value));
	}

	public Optional<JsonValue<?>> remove(String key) {
		Optional<JsonEntry<?>> option = search(key);
		if (option.isPresent()) {
			synchronized (values) {
				values.remove(option.get());
			}
		}
		return option.map(JsonEntry::getValue);
	}

	public JsonValue<?> get(String key) {
		return optional(key).orElse(null);
	}

	public Optional<JsonValue<?>> optional(String key) {
		return search(key).map(JsonEntry::getValue);
	}

	public String key(JsonValue<?> value) {
		return search(value).map(JsonEntry::getKey).orElse(null);
	}

	public boolean has(String key) {
		return search(key).isPresent();
	}

	public boolean has(String key, ValueType type) {
		return optional(key).filter(value -> value.hasType(type)).isPresent();
	}

	public int size() {
		synchronized (values) {
			return values.size();
		}
	}

	public String[] keys() {
		synchronized (values) {
			return values.stream().map(JsonEntry::getKey).toArray(String[]::new);
		}
	}

	public JsonValue<?>[] values() {
		synchronized (values) {
			return values.stream().map(JsonEntry::getKey).toArray(JsonValue<?>[]::new);
		}
	}

	public JsonEntry<?>[] entries() {
		synchronized (values) {
			return values.toArray(new JsonEntry<?>[0]);
		}
	}

	@Override
	public SynchronizedIterator<JsonEntry<?>> iterator() {
		return new SynchronizedIterator<>(values);
	}

	/*
	 * Helper
	 */

	private Optional<JsonEntry<?>> search(String key) {
		synchronized (values) {
			return values.stream().filter(entry -> entry.getKey().equals(key)).findFirst();
		}
	}

	private Optional<JsonEntry<?>> search(JsonValue<?> value) {
		synchronized (values) {
			return values.stream().filter(entry -> entry.getValue().equals(value)).findFirst();
		}
	}

}
