package com.syntaxphoenix.syntaxapi.json.utils;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;

public final class LockedIterator<E> implements Iterator<E> {

    private final Lock lock;
    private final Iterator<E> iterator;

    public LockedIterator(Lock lock, Iterator<E> iterator) {
        this.lock = lock;
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        lock.lock();
        try {
            return iterator.hasNext();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E next() {
        lock.lock();
        try {
            return iterator.next();
        } finally {
            lock.unlock();
        }
    }

}
