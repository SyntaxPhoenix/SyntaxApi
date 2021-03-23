package com.syntaxphoenix.syntaxapi.utils.key;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class Namespace extends AbstractNamespace<NamespacedKey> {

    private static final ConcurrentHashMap<String, Namespace> NAMESPACES = new ConcurrentHashMap<>();

    public static Namespace of(String name) {
        if (NAMESPACES.containsKey(name)) {
            return NAMESPACES.get(name);
        }
        Namespace namespace = new Namespace(name);
        NAMESPACES.put(name, namespace);
        return namespace;
    }

    protected final String name;
    protected final ConcurrentHashMap<String, NamespacedKey> keys = new ConcurrentHashMap<>();

    private Namespace(String name) {
        this.name = name;
        NAMESPACES.put(name, this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean contains(String key) {
        return keys.containsKey(key);
    }

    @Override
    public NamespacedKey createNamed(String key, String name) {
        return keys.computeIfAbsent(key, compute -> new NamespacedKey(this, compute, name));
    }

    @Override
    public NamespacedKey create(String key) {
        return keys.computeIfAbsent(key, compute -> new NamespacedKey(this, compute));
    }

    @Override
    public Optional<NamespacedKey> option(String key) {
        return Optional.ofNullable(keys.get(key));
    }

    @Override
    public NamespacedKey get(String key) {
        return keys.get(key);
    }

    @Override
    public String[] getKeyspaces() {
        return keys.keySet().toArray(new String[0]);
    }

    @Override
    public NamespacedKey[] getKeys() {
        return keys.values().toArray(new NamespacedKey[0]);
    }

}
