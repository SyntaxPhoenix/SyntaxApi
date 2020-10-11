package com.syntaxphoenix.syntaxapi.data;

import java.util.Set;

import com.syntaxphoenix.syntaxapi.data.key.DataKey;

public interface IDataContainer {

	boolean has(String key);

	boolean has(DataKey key);

	boolean has(String key, DataType<?, ?> type);

	boolean has(DataKey key, DataType<?, ?> type);

	<E> E get(String key, DataType<?, E> type);

	<E> E get(DataKey key, DataType<?, E> type);

	/*
	 * Abstract
	 */

	DataAdapterContext getAdapterContext();

	Object get(String key);

	Object get(DataKey key);

	<E, V> void set(String key, E value, DataType<V, E> type);

	<E, V> void set(DataKey key, E value, DataType<V, E> type);

	boolean remove(String key);

	boolean remove(DataKey key);

	Set<String> getKeys();

	DataKey[] getDataKeys();

	boolean isEmpty();

	int size();

}
