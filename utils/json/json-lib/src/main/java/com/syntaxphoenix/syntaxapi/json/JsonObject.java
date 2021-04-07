package com.syntaxphoenix.syntaxapi.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.syntaxphoenix.syntaxapi.json.utils.LockedIterator;

public class JsonObject extends JsonValue<Map<String, JsonValue<?>>> implements Iterable<JsonEntry<?>> {

    private final ArrayList<JsonEntry<?>> values = new ArrayList<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock read = lock.readLock();
    private final Lock write = lock.writeLock();

    @Override
    public final ValueType getType() {
        return ValueType.OBJECT;
    }

    @Override
    public Map<String, JsonValue<?>> getValue() {
        HashMap<String, JsonValue<?>> values = new HashMap<>();
        read.lock();
        for (JsonEntry<?> entry : this.values) {
            values.put(entry.getKey(), entry.getValue());
        }
        read.unlock();
        return values;
    }

    public JsonObject set(String key, JsonValue<?> value) {
        if (value == null) {
            return this;
        }
        remove(key);
        write.lock();
        values.add(new JsonEntry<>(key, value));
        write.unlock();
        return this;
    }

    public JsonObject set(String key, Object value) {
        return set(key, JsonValue.fromPrimitive(value));
    }

    public Optional<JsonValue<?>> remove(String key) {
        Optional<JsonEntry<?>> option = search(key);
        if (option.isPresent()) {
            write.lock();
            values.remove(option.get());
            write.unlock();
        }
        return option.map(JsonEntry::getValue);
    }

    public JsonValue<?> get(String key) {
        return optional(key).orElse(null);
    }

    public Optional<JsonValue<?>> optional(String key) {
        return search(key).map(JsonEntry::getValue);
    }

    public String key(JsonValue<?> value) {
        return search(value).map(JsonEntry::getKey).orElse(null);
    }

    public boolean has(String key) {
        return search(key).isPresent();
    }

    public boolean has(String key, ValueType type) {
        return optional(key).filter(value -> value.hasType(type)).isPresent();
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public String[] keys() {
        read.lock();
        try {
            return values.stream().map(JsonEntry::getKey).toArray(String[]::new);
        } finally {
            read.unlock();
        }
    }

    public JsonValue<?>[] values() {
        read.lock();
        try {
            return values.stream().map(JsonEntry::getKey).toArray(JsonValue<?>[]::new);
        } finally {
            read.unlock();
        }
    }

    public JsonEntry<?>[] entries() {
        read.lock();
        try {
            return values.toArray(new JsonEntry<?>[0]);
        } finally {
            read.unlock();
        }
    }

    @Override
    public LockedIterator<JsonEntry<?>> iterator() {
        return new LockedIterator<>(read, values.iterator());
    }

    /*
     * Helper
     */

    private Optional<JsonEntry<?>> search(String key) {
        read.lock();
        try {
            return values.stream().filter(entry -> entry.getKey().equals(key)).findFirst();
        } finally {
            read.unlock();
        }
    }

    private Optional<JsonEntry<?>> search(JsonValue<?> value) {
        read.lock();
        try {
            return values.stream().filter(entry -> entry.getValue().equals(value)).findFirst();
        } finally {
            read.unlock();
        }
    }

}
