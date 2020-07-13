package com.mana_wars.ui.textures;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FixedSizeTexture extends AdaptiveTexture {

    public FixedSizeTexture(String fileName, ImageFormat format) {
        super(fileName, format);
    }

    @Override
    protected TextureRegion adapt(FileHandle file) {
        return new TextureRegion(new Texture(file));
    }

}
