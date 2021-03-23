package com.syntaxphoenix.syntaxapi.utils.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class CacheMap<K, V> {

    private final Map<K, CachedObject<V>> cacheMap;
    private final long timeToLive;

    private final Consumer<ArrayList<CacheEntry<K, V>>> action;
    private final Thread timer;

    public CacheMap(long timeToLive, final long timerInterval, int maxItems) {
        this(timeToLive, timerInterval, maxItems, null);
    }

    public CacheMap(long timeToLive, final long timerInterval, int maxItems, Consumer<ArrayList<CacheEntry<K, V>>> action) {
        this.cacheMap = Collections.synchronizedMap(new HashMap<>(maxItems));
        this.timeToLive = timeToLive * 1000;
        this.action = action;
        if (timeToLive > 0 && timerInterval > 0) {
            timer = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(timerInterval * 1000);
                    } catch (InterruptedException ex) {
                    }
                    cleanup();
                }
            });
        } else {
            timer = null;
        }
    }

    public void put(K key, V value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CachedObject<V>(value));
        }
    }

    public V get(K key) {
        synchronized (cacheMap) {
            CachedObject<V> object = cacheMap.get(key);
            if (object == null) {
                return null;
            }
            return object.getValue();
        }
    }

    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public boolean doesAutomaticCleanup() {
        return timer != null;
    }

    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<CacheEntry<K, V>> delete = null;

        synchronized (cacheMap) {
            Iterator<Entry<K, CachedObject<V>>> iterator = cacheMap.entrySet().iterator();
            delete = new ArrayList<>((cacheMap.size() / 2) + 1);

            while (iterator.hasNext()) {
                Entry<K, CachedObject<V>> entry = iterator.next();
                CachedObject<V> object = entry.getValue();
                if (object != null && (now > (timeToLive + object.getLastAccess()))) {
                    delete.add(new CacheEntry<>(entry.getKey(), object.getValue(false)));
                }
            }
        }

        if (action != null) {
            action.accept(delete);
        }

        for (CacheEntry<K, V> key : delete) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
            Thread.yield();
        }
    }

    public static class CacheEntry<L, B> implements Entry<L, B> {

        private final L key;
        private final B value;

        protected CacheEntry(L key, B value) {
            this.value = value;
            this.key = key;
        }

        @Override
        public L getKey() {
            return key;
        }

        @Override
        public B getValue() {
            return value;
        }

        @Override
        public B setValue(B value) {
            return this.value;
        }

    }

}
