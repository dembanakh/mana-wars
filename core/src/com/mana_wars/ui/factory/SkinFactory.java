package com.mana_wars.ui.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;

public class SkinFactory extends AssetFactoryBuilder<String, Skin, String> {
    public SkinFactory(String... files) {
        super(files);
    }

    @Override
    protected String key(String file) {
        return file;
    }

    @Override
    protected Skin loadAsset(String file) {
        final String path = String.format(UIStringConstants.UI_SKIN.FORMAT, file);
        Skin skin = new Skin(Gdx.files.internal(path));
        for (BitmapFont font : skin.getAll(BitmapFont.class).values()) {
            font.getData().setScale(1.5f);
        }
        return skin;
    }
}
