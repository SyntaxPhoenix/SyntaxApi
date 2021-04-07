package com.syntaxphoenix.syntaxapi.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.syntaxphoenix.syntaxapi.json.utils.LockedIterator;

public class JsonArray extends JsonValue<List<JsonValue<?>>> implements Iterable<JsonValue<?>> {

    private final ArrayList<JsonValue<?>> values = new ArrayList<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock read = lock.readLock();
    private final Lock write = lock.writeLock();

    @Override
    public final ValueType getType() {
        return ValueType.ARRAY;
    }

    @Override
    public List<JsonValue<?>> getValue() {
        return values;
    }

    public JsonArray add(JsonValue<?> value) {
        if (value == null) {
            return this;
        }
        write.lock();
        values.add(value);
        write.unlock();
        return this;
    }

    public JsonArray add(Object value) {
        return add(JsonValue.fromPrimitive(value));
    }

    public Optional<JsonValue<?>> remove(int index) {
        if (index >= size() || index < 0) {
            return Optional.empty();
        }
        write.lock();
        try {
            return Optional.ofNullable(values.remove(index));
        } finally {
            write.unlock();
        }
    }

    public boolean remove(JsonValue<?> value) {
        write.lock();
        try {
            return values.remove(value);
        } finally {
            write.unlock();
        }
    }

    public JsonValue<?> get(int index) {
        return optional(index).orElse(null);
    }

    public Optional<JsonValue<?>> optional(int index) {
        if (index >= size() || index < 0) {
            return Optional.empty();
        }
        synchronized (values) {
            return Optional.ofNullable(values.get(index));
        }
    }

    public boolean has(JsonValue<?> value) {
        read.lock();
        try {
            return values.contains(value);
        } finally {
            read.unlock();
        }
    }

    public boolean has(int index) {
        return optional(index).isPresent();
    }

    public boolean has(int index, ValueType type) {
        return optional(index).filter(value -> value.hasType(type)).isPresent();
    }

    public int indexOf(JsonValue<?> value) {
        read.lock();
        try {
            return values.indexOf(value);
        } finally {
            read.unlock();
        }
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public LockedIterator<JsonValue<?>> iterator() {
        return new LockedIterator<>(read, values.iterator());
    }

    public JsonValue<?>[] toArray() {
        read.lock();
        try {
            return values.toArray(new JsonValue<?>[0]);
        } finally {
            read.unlock();
        }
    }

}
