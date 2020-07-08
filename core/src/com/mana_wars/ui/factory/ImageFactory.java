package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.Texture;
import com.mana_wars.ui.textures.AdaptiveTexture;

public class ImageFactory extends AssetFactoryBuilder<String, Texture, AdaptiveTexture> {

    public ImageFactory(AdaptiveTexture... textures) {
        super(textures);
    }

    @Override
    protected Entry<String, Texture> process(AdaptiveTexture texture) {
        return new Entry<>(texture.getFileName(), texture.adapt());
    }

}
