package com.mana_wars.ui.factory;

import java.util.HashMap;

public abstract class AssetFactory<V, T> {

    private final String[] fileNames;
    private final HashMap<V, T> items = new HashMap<>();

    protected AssetFactory(String... fileNames) {
        this.fileNames = fileNames;
    }

    public T getAsset(V id) {
        return items.get(id);
    }

    public final void loadItems() {
        loadItems(fileNames);
    }

    protected abstract void loadItems(String[] fileNames);

    protected void addAsset(V key, T asset) {
        items.put(key, asset);
    }

}
