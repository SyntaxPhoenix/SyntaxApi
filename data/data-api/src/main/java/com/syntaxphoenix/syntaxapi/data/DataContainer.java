package com.syntaxphoenix.syntaxapi.data;

import java.util.Set;

public abstract class DataContainer {

	public boolean has(String key) {
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

	public <E> E get(String key, DataType<?, E> type) {
		return type.fromPrimitiveObj(getAdapterContext(), get(key));
	}

	/*
	 * Abstract
	 */

	public abstract DataAdapterContext getAdapterContext();

	public abstract Object get(String key);

	public abstract <E, V> void set(String key, E value, DataType<V, E> type);

	public abstract boolean remove(String key);

	public abstract Set<String> getKeys();

	public abstract boolean isEmpty();

	public abstract int size();

}
