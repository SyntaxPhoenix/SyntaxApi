package com.syntaxphoenix.syntaxapi.utils.key;

public abstract class AbstractKeyed implements IKeyed {

    protected final IKey key;

    public AbstractKeyed(IKey key) {
        this.key = key;
    }

    @Override
    public IKey getKey() {
        return key;
    }

}
