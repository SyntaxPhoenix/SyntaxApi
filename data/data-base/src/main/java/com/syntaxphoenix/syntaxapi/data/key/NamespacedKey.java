package com.syntaxphoenix.syntaxapi.data.key;

public class NamespacedKey extends DataKey {

	protected final Namespace namespace;
	protected final String key;

	private NamespacedKey(String[] parts) {
		this.namespace = new Namespace(parts[0]);
		this.key = parts[1];
	}

	protected NamespacedKey(Namespace namespace, String key) {
		this.namespace = namespace;
		this.key = key;
	}

	public final Namespace getNamespace() {
		return namespace;
	}

	public final String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return namespace.getName() + ':' + key;
	}

	public static NamespacedKey fromString(String key) {
		return key.contains(":") ? new NamespacedKey(key.split(":", 2)) : null;
	}

	public static NamespacedKey fromStringOrDefault(String key, String namespace) {
		return key.contains(":") ? new NamespacedKey(key.split(":", 2))
			: new NamespacedKey(new String[] {
					namespace,
					key
			});
	}

}
