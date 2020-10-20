package com.syntaxphoenix.syntaxapi.utils.key;

import java.util.function.Function;
import java.util.regex.Matcher;

public final class NamespacedKey extends AbstractNamedKey {

	public static NamespacedKey of(String namespace, String key) {
		return Namespace.of(namespace).create(key);
	}

	public static NamespacedKey of(String namespace, String key, String name) {
		return Namespace.of(namespace).createNamed(key, name);
	}

	public static NamespacedKey fromStringOrCompute(String string, Function<String, NamespacedKey> mapper) {
		NamespacedKey computed = fromString(string);
		return computed == null ? mapper.apply(string) : computed;
	}

	public static NamespacedKey fromStringOrDefault(String string, NamespacedKey key) {
		NamespacedKey computed = fromString(string);
		return computed == null ? key : computed;
	}

	public static NamespacedKey fromString(String string) {
		Matcher keyMatcher = KeyConstants.KEY_PATTERN.matcher(string);
		if (keyMatcher.matches()) {
			String namespace = keyMatcher.group("Namespace");
			String key = keyMatcher.group("Key");
			return of(namespace, key);
		}
		keyMatcher = KeyConstants.NAMED_KEY_PATTERN.matcher(string);
		if (keyMatcher.matches()) {
			String namespace = keyMatcher.group("Namespace");
			String name = keyMatcher.group("Name");
			String key = keyMatcher.group("Key");
			return of(namespace, key, name);
		}
		return null;
	}

	private final Namespace namespace;
	private final String name;
	private final String key;

	protected NamespacedKey(Namespace namespace, String key) {
		this(namespace, key, key.toUpperCase());
	}

	protected NamespacedKey(Namespace namespace, String key, String name) {
		this.namespace = namespace;
		this.name = name;
		this.key = key;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Namespace getNamespace() {
		return namespace;
	}

	@Override
	public String getKey() {
		return key;
	}

}
