package com.syntaxphoenix.syntaxapi.utils.key;

public interface INamedKey extends IKey {

	String getName();

	default String asNamedString() {
		return String.format(KeyConstants.NAMED_KEY_FORMAT, getNamespace().getName(), getKey(), getName());
	}

	default int hashNamed() {
		return asNamedString().hashCode();
	}

	default boolean isSame(INamedKey key) {
		return isSimilar(key) && key.getName().equals(getName());
	}

	public static boolean isNamedKey(String string) {
		return KeyConstants.NAMED_KEY_PATTERN.matcher(string).matches();
	}

}
