package com.syntaxphoenix.syntaxapi.utils.key;

public abstract class AbstractNamespaced implements INamespaced {

    protected final INamespace<?> namespace;

    public AbstractNamespaced(INamespace<?> namespace) {
        this.namespace = namespace;
    }

    @Override
    public INamespace<?> getNamespace() {
        return namespace;
    }

}
