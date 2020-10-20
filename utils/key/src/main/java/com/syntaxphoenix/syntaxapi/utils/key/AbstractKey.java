package com.syntaxphoenix.syntaxapi.utils.key;

public abstract class AbstractKey implements IKey {

	@Override
	public boolean equals(Object obj) {
		return obj instanceof IKey && isSimilar((IKey) obj);
	}

}
