package ru.otus.core.cache;


import java.util.ArrayList;
import java.util.WeakHashMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HwCacheImpl<K, V> implements HwCache<K, V> {
    private final WeakHashMap<K, V> stored = new WeakHashMap<>();

    private final ArrayList<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        stored.put(key, value);
        notifyListeners(key, value, Action.PUT);
    }

    @Override
    public void remove(K key) {
        stored.remove(key);
        notifyListeners(key, null, Action.REMOVE);
    }

    @Override
    public V get(K key) {
        var value = stored.get(key);
        notifyListeners(key, value, Action.GET);
        return value;
    }

    public long size() {
        return stored.size();
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, Action action) {
        for (var listener : listeners) {
            try {
                listener.notify(key, value, action.toString());
            } catch (Exception e) {
                log.error("failed to notify listener {}", listener, e);
            }
        }
    }

    private enum Action {
        PUT, GET, REMOVE
    }
}
