package com.syntaxphoenix.syntaxapi.data.key;

public class Namespace {

	protected final String name;

	public Namespace(String name) {
		this.name = name;
	}

	public NamespacedKey createKey(String key) {
		return new NamespacedKey(this, key);
	}

	public final String getName() {
		return name;
	}

}
