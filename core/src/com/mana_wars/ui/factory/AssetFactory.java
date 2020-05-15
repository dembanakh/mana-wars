package com.mana_wars.ui.factory;

import java.util.HashMap;

public abstract class AssetFactory<V, T> {

    protected final String[] fileNames;
    protected final HashMap<V, T> items = new HashMap<>();

    protected AssetFactory(String... fileNames) {
        this.fileNames = fileNames;
    }

    public T getAsset(V id) {
        return items.get(id);
    }

    public abstract void loadItems();

}
