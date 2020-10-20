package com.syntaxphoenix.syntaxapi.data;

import java.util.Set;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;

public interface IDataContainer {

	boolean has(String key);

	boolean has(IKey key);

	boolean has(String key, DataType<?, ?> type);

	boolean has(IKey key, DataType<?, ?> type);

	<E> E get(String key, DataType<?, E> type);

	<E> E get(IKey key, DataType<?, E> type);

	/*
	 * Abstract
	 */

	DataAdapterContext getAdapterContext();

	Object get(String key);

	Object get(IKey key);

	<E, V> void set(String key, E value, DataType<V, E> type);

	<E, V> void set(IKey key, E value, DataType<V, E> type);

	boolean remove(String key);

	boolean remove(IKey key);

	Set<String> getKeyspaces();

	IKey[] getKeys();

	boolean isEmpty();

	int size();

}
