package com.syntaxphoenix.syntaxapi.utils.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CacheList<V> {

    private final List<CachedObject<V>> cacheList;
    private final long timeToLive;

    private final Consumer<ArrayList<V>> action;
    private final Thread timer;

    public CacheList(long timeToLive, final long timerInterval, int maxItems) {
        this(timeToLive, timerInterval, maxItems, null);
    }

    public CacheList(long timeToLive, final long timerInterval, int maxItems, Consumer<ArrayList<V>> action) {
        this.cacheList = Collections.synchronizedList(new ArrayList<>(maxItems));
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

    public ArrayList<V> getListCopy() {
        ArrayList<V> values = new ArrayList<>(size());
        synchronized (cacheList) {
            Iterator<CachedObject<V>> iterator = cacheList.iterator();
            while (iterator.hasNext()) {
                values.add(iterator.next().getValue());
            }
        }
        return values;
    }

    public void add(V value) {
        synchronized (cacheList) {
            cacheList.add(new CachedObject<V>(value));
        }
    }

    public V get(int index) {
        synchronized (cacheList) {
            CachedObject<V> object = cacheList.get(index);
            if (object == null) {
                return null;
            }
            return object.getValue();
        }
    }

    public Optional<V> predicateFilter(Predicate<V> predicate) {
        synchronized (cacheList) {
            return cacheList.stream().filter(object -> predicate.test(object.getValue(false))).findFirst()
                .map(cachedObject -> cachedObject.getValue());
        }
    }

    public Optional<V> streamFilter(Function<Stream<V>, Optional<V>> filter) {
        synchronized (cacheList) {
            Optional<V> filtered = filter.apply(cacheList.stream().map(object -> object.getValue(false)));
            return !filtered.isPresent() ? filtered
                : cacheList.stream().filter(object -> object.getValue(false) == filtered.get()).findFirst()
                    .map(object -> object.getValue());
        }
    }

    public boolean contains(V value) {
        synchronized (cacheList) {
            return cacheList.stream().anyMatch(object -> object.getValue(false) == value);
        }
    }

    public void forEach(Consumer<V> consume) {
        synchronized (cacheList) {
            cacheList.stream().map(object -> object.getValue()).forEach(consume);
        }
    }

    public void remove(int index) {
        synchronized (cacheList) {
            cacheList.remove(index);
        }
    }

    public void remove(V value) {
        synchronized (cacheList) {
            cacheList.remove(value);
        }
    }

    public int size() {
        synchronized (cacheList) {
            return cacheList.size();
        }
    }

    public boolean doesAutomaticCleanup() {
        return timer != null;
    }

    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<V> delete = null;

        synchronized (cacheList) {
            Iterator<CachedObject<V>> iterator = cacheList.iterator();
            delete = new ArrayList<>((cacheList.size() / 2) + 1);

            while (iterator.hasNext()) {
                CachedObject<V> object = iterator.next();
                if (object != null && (now > (timeToLive + object.getLastAccess()))) {
                    delete.add(object.getValue(false));
                }
            }
        }

        if (action != null) {
            action.accept(delete);
        }

        for (V value : delete) {
            synchronized (cacheList) {
                cacheList.remove(value);
            }
            Thread.yield();
        }
    }

}
