package com.mana_wars.ui.textures;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FixedSizeMirroredTexture extends AdaptiveTexture {

    public FixedSizeMirroredTexture(String fileName, ImageFormat format) {
        super(fileName, format);
    }

    @Override
    public String getFileName() {
        return super.getFileName() + "~mirrored";
    }

    @Override
    protected TextureRegion adapt(FileHandle file) {
        TextureRegion region = new TextureRegion(new Texture(file));
        region.flip(true, false);
        return region;
    }

}
