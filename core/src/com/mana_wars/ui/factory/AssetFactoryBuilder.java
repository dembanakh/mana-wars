package com.mana_wars.ui.factory;

import java.util.HashMap;
import java.util.Map;

abstract class AssetFactoryBuilder<K, V, I> {

    private final I[] files;

    AssetFactoryBuilder(I[] files) {
        this.files = files;
    }

    protected abstract K key(I file);
    protected abstract V loadAsset(I file);

    public AssetFactory<K, V> build() {
        Map<K, V> items = new HashMap<>();
        for (I file : files) {
            items.put(key(file), loadAsset(file));
        }
        return new AssetFactory<>(items);
    }
}
