package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.Texture;
import com.mana_wars.ui.textures.AdaptiveTexture;

public class ImageFactory extends AssetFactoryBuilder<String, Texture, AdaptiveTexture> {

    public ImageFactory(AdaptiveTexture... textures) {
        super(textures);
    }

    @Override
    protected String key(AdaptiveTexture texture) {
        return texture.getFileName();
    }

    @Override
    protected Texture loadAsset(AdaptiveTexture texture) {
        return texture.adapt();
    }

}
