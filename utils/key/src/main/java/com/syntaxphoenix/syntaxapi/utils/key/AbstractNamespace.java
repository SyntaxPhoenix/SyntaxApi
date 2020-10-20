package com.syntaxphoenix.syntaxapi.utils.key;

public abstract class AbstractNamespace<E extends IKey> implements INamespace<E> {

	@Override
	public boolean equals(Object obj) {
		return obj instanceof INamespace && isSimilar((INamespace<?>) obj);
	}

}
