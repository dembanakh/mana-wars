package com.mana_wars.ui.textures;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class FixedSizeTexture extends AdaptiveTexture {

    public FixedSizeTexture(String fileName, ImageFormat format) {
        super(fileName, format);
    }

    @Override
    protected Texture adapt(FileHandle file) {
        return new Texture(file);
    }

}
