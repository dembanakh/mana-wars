package com.mana_wars.ui.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    // TEMP
    private static TextureRegion whiteTexture;

    public static TextureRegion getWhiteTexture() {
        if (whiteTexture == null) {
            whiteTexture = new TextureRegion(new Texture(Gdx.files.internal("white.png")));
        }
        return whiteTexture;
    }

}
