package com.syntaxphoenix.syntaxapi.data;

import com.syntaxphoenix.syntaxapi.data.key.DataKey;
import com.syntaxphoenix.syntaxapi.data.key.NamespacedKey;

public abstract class DataContainer implements IDataContainer {

	public boolean has(String key) {
		return get(key) != null;
	}

	public boolean has(DataKey key) {
		return get(key) != null;
	}

	public boolean has(String key, DataType<?, ?> type) {
		Object obj = get(key);
		if (obj == null)
			return false;
		if (type.isPrimitive(obj))
			return false;
		return true;
	}

	public boolean has(DataKey key, DataType<?, ?> type) {
		Object obj = get(key);
		if (obj == null)
			return false;
		if (type.isPrimitive(obj))
			return false;
		return true;
	}

	public <E> E get(String key, DataType<?, E> type) {
		return type.fromPrimitiveObj(getAdapterContext(), get(key));
	}

	public <E> E get(DataKey key, DataType<?, E> type) {
		return type.fromPrimitiveObj(getAdapterContext(), get(key));
	}

	public Object get(DataKey key) {
		return get(key.toString());
	}

	public <E, V> void set(DataKey key, E value, DataType<V, E> type) {
		set(key.toString(), value, type);
	}

	public boolean remove(DataKey key) {
		return remove(key.toString());
	}

	public DataKey[] getDataKeys() {
		return getKeys().stream().map(NamespacedKey::fromString).toArray(DataKey[]::new);
	}

}
