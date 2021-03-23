package com.syntaxphoenix.syntaxapi.json.utils;

import java.util.Collection;
import java.util.Iterator;

public final class SynchronizedIterator<E> implements Iterator<E> {

    private final Collection<E> owner;
    private final Iterator<E> iterator;

    public SynchronizedIterator(Collection<E> owner) {
        this.owner = owner;
        this.iterator = owner.iterator();
    }

    public Collection<E> getOwner() {
        return owner;
    }

    @Override
    public boolean hasNext() {
        synchronized (owner) {
            return iterator.hasNext();
        }
    }

    @Override
    public E next() {
        synchronized (owner) {
            return iterator.next();
        }
    }

}
