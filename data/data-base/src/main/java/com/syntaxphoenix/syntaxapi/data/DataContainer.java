package com.syntaxphoenix.syntaxapi.data;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;
import com.syntaxphoenix.syntaxapi.utils.key.NamespacedKey;

public abstract class DataContainer implements IDataContainer {

    public boolean has(String key) {
        return get(key) != null;
    }

    public boolean has(IKey key) {
        return get(key) != null;
    }

    public boolean has(String key, DataType<?, ?> type) {
        Object obj = get(key);
        if (obj == null) {
            return false;
        }
        if (type.isPrimitive(obj)) {
            return false;
        }
        return true;
    }

    public boolean has(IKey key, DataType<?, ?> type) {
        Object obj = get(key);
        if (obj == null) {
            return false;
        }
        if (type.isPrimitive(obj)) {
            return false;
        }
        return true;
    }

    public <E> E get(String key, DataType<?, E> type) {
        return type.fromPrimitiveObj(getAdapterContext(), get(key));
    }

    public <E> E get(IKey key, DataType<?, E> type) {
        return type.fromPrimitiveObj(getAdapterContext(), get(key));
    }

    public Object get(IKey key) {
        return get(key.asString());
    }

    public <E, V> void set(IKey key, E value, DataType<V, E> type) {
        set(key.asString(), value, type);
    }

    public boolean remove(IKey key) {
        return remove(key.asString());
    }

    public IKey[] getKeys() {
        return getKeyspaces().stream().map(NamespacedKey::fromString).toArray(IKey[]::new);
    }

}
