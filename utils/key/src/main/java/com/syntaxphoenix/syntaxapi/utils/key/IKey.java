package com.syntaxphoenix.syntaxapi.utils.key;

public interface IKey {

	INamespace<?> getNamespace();

	String getKey();

	default boolean isSimilar(IKey key) {
		return key != null && (key.getKey().equals(getKey()) && getNamespace().isSimilar(key.getNamespace()));
	}
	
	default int hash() {
		return asString().hashCode();
	}

	default String asString() {
		return String.format(KeyConstants.KEY_FORMAT, getNamespace().getName(), getKey());
	}

	public static boolean isKey(String string) {
		return KeyConstants.KEY_PATTERN.matcher(string).matches();
	}

}
