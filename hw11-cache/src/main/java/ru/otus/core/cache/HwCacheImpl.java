package ru.otus.core.cache;


import java.util.ArrayList;
import java.util.WeakHashMap;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HwCacheImpl<K, V> implements HwCache<K, V> {
    private final WeakHashMap<K, V> stored = new WeakHashMap<>();

    private final ArrayList<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        stored.put(key, value);
        listeners.forEach(x -> x.notify(key, value, "PUT"));
    }

    @Override
    public void remove(K key) {
        stored.remove(key);
        listeners.forEach(x -> x.notify(key, null, "REMOVE"));
    }

    @Override
    public V get(K key) {
        var value = stored.get(key);
        listeners.forEach(x -> x.notify(key, value, "GET"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
