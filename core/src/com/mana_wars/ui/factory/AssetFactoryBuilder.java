package com.mana_wars.ui.factory;

import java.util.HashMap;
import java.util.Map;

abstract class AssetFactoryBuilder<K, V, I> {

    private final I[] files;

    AssetFactoryBuilder(I[] files) {
        this.files = files;
    }

    protected abstract Entry<K, V> process(I file);

    public AssetFactory<K, V> build() {
        Map<K, V> items = new HashMap<>();
        for (I file : files) {
            Entry<K, V> entry = process(file);
            items.put(entry.key, entry.value);
        }
        return new AssetFactory<>(items);
    }

    protected static class Entry<K, V> {
        private final K key;
        private final V value;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
