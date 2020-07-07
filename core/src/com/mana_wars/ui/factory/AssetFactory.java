package com.mana_wars.ui.factory;

import java.util.Map;

public class AssetFactory<K, V> {

    private final Map<K, V> items;

    AssetFactory(Map<K, V> items) {
        this.items = items;
    }

    public V getAsset(K id) {
        return items.get(id);
    }

}
