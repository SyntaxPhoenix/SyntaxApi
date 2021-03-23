package com.syntaxphoenix.syntaxapi.utils.key;

public abstract class AbstractNamedKey implements INamedKey {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof INamedKey && isSame((INamedKey) obj);
    }

}
