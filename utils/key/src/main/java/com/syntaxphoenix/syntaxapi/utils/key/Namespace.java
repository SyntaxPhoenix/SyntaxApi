package com.syntaxphoenix.syntaxapi.utils.key;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Namespace extends AbstractNamespace<NamespacedKey> {

	private static final Map<String, Namespace> NAMESPACES = Collections.synchronizedMap(new HashMap<>());

	public static Namespace of(String name) {
		return NAMESPACES.computeIfAbsent(name, namespace -> new Namespace(namespace));
	}

	protected final String name;
	protected final Map<String, NamespacedKey> keys = Collections.synchronizedMap(new HashMap<>());

	private Namespace(String name) {
		this.name = name;
		NAMESPACES.put(name, this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean contains(String key) {
		return keys.containsKey(key);
	}

	@Override
	public NamespacedKey createNamed(String key, String name) {
		return keys.computeIfAbsent(key, compute -> new NamespacedKey(this, compute, name));
	}

	@Override
	public NamespacedKey create(String key) {
		return keys.computeIfAbsent(key, compute -> new NamespacedKey(this, compute));
	}

	@Override
	public Optional<NamespacedKey> option(String key) {
		return Optional.ofNullable(keys.get(key));
	}

	@Override
	public NamespacedKey get(String key) {
		return keys.get(key);
	}

	@Override
	public String[] getKeyspaces() {
		return keys.keySet().toArray(new String[0]);
	}

	@Override
	public NamespacedKey[] getKeys() {
		return keys.values().toArray(new NamespacedKey[0]);
	}

}
