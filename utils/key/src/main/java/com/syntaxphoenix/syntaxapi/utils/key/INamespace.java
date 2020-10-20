package com.syntaxphoenix.syntaxapi.utils.key;

import java.util.Optional;

public interface INamespace<E extends IKey> {

	String getName();

	boolean contains(String key);

	E createNamed(String key, String name);

	E create(String key);

	Optional<E> option(String key);

	E get(String key);

	String[] getKeyspaces();

	E[] getKeys();

	default int hash() {
		return getName().hashCode();
	}

	default boolean isSimilar(INamespace<?> namespace) {
		return namespace != null && getName().equals(namespace.getName());
	}

}
