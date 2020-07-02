package com.mana_wars.ui.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public abstract class AdaptiveTexture {

    private final String fileName;
    private final ImageFormat format;

    AdaptiveTexture(String fileName, ImageFormat format) {
        this.fileName = fileName;
        this.format = format;
    }

    public Texture adapt() {
        return adapt(Gdx.files.internal(fileName + format));
    }

    protected abstract Texture adapt(FileHandle file);

    public String getFileName() {
        return fileName;
    }

    public enum ImageFormat {
        JPG {
            @Override
            public String toString() {
                return ".jpg";
            }
        }, PNG {
            @Override
            public String toString() {
                return ".png";
            }
        }
    }

}
