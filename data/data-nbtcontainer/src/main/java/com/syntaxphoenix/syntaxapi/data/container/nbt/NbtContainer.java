package com.syntaxphoenix.syntaxapi.data.container.nbt;

import java.util.Set;

import com.syntaxphoenix.syntaxapi.data.DataAdapterContext;
import com.syntaxphoenix.syntaxapi.data.DataContainer;
import com.syntaxphoenix.syntaxapi.data.DataType;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.utils.NbtStorage;
import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

public class NbtContainer extends DataContainer implements DataAdapterContext, NbtStorage<NbtCompound> {

    private final NbtCompound root;

    private final NbtAdapterRegistry registry;

    public NbtContainer(NbtAdapterRegistry registry) {
        this(new NbtCompound(), registry);
    }

    protected NbtContainer(NbtCompound root, NbtAdapterRegistry registry) {
        this.root = root;
        this.registry = registry;
    }

    @Override
    public NbtContainer newDataContainer() {
        return new NbtContainer(registry);
    }

    @Override
    public DataAdapterContext getAdapterContext() {
        return this;
    }

    public NbtAdapterRegistry getAdapterRegistry() {
        return registry;
    }

    public NbtCompound getRoot() {
        return root;
    }

    @Override
    public Object get(String key) {
        NbtTag tag = root.get(key);
        if (tag == null) {
            return tag;
        }
        return registry.extract(tag);
    }

    public void set(String key, NbtTag tag) {
        if (tag == null) {
            return;
        }
        root.set(key, tag);
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Object object) {
        set(key, registry.wrap(Primitives.fromPrimitive((Class<Object>) object.getClass()), object));
    }

    @Override
    public <E, V> void set(String key, E value, DataType<V, E> type) {
        set(key, registry.wrap(type.getPrimitive(), type.toPrimitive(getAdapterContext(), value)));
    }

    @Override
    public boolean remove(String key) {
        return root.remove(key) != null;
    }

    @Override
    public Set<String> getKeyspaces() {
        return root.getKeys();
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public void fromNbt(NbtCompound nbt) {
        root.clear();
        for (String key : nbt.getKeys()) {
            root.set(key, nbt.get(key));
        }
    }

    @Override
    public NbtCompound asNbt() {
        return root.clone();
    }

}
