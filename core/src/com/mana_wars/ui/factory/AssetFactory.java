package com.mana_wars.ui.factory;

import java.util.HashMap;

public abstract class AssetFactory<V, T> {

    private final HashMap<V, T> items = new HashMap<>();

    protected AssetFactory(String... fileNames) {
        loadItems(fileNames);
    }

    public T getAsset(V id) {
        return items.get(id);
    }

    protected abstract void loadItems(String[] fileNames);

    protected void addAsset(V key, T asset) {
        items.put(key, asset);
    }

}
