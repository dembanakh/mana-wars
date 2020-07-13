package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mana_wars.ui.textures.AdaptiveTexture;

public class ImageFactory extends AssetFactoryBuilder<String, TextureRegion, AdaptiveTexture> {

    public ImageFactory(AdaptiveTexture... textures) {
        super(textures);
    }

    @Override
    protected Entry<String, TextureRegion> process(AdaptiveTexture texture) {
        return new Entry<>(texture.getFileName(), texture.adapt());
    }

}
