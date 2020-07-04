package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.Texture;
import com.mana_wars.ui.textures.AdaptiveTexture;

public class ImageFactory extends AssetFactory<String, Texture> {

    private AdaptiveTexture[] textures;

    public ImageFactory(AdaptiveTexture... textures) {
        super(extractFileNames(textures));
        this.textures = textures;
    }

    private static String[] extractFileNames(AdaptiveTexture[] textures) {
        String[] fileNames = new String[textures.length];
        for (int i = 0; i < textures.length; ++i) {
            fileNames[i] = textures[i].getFileName();
        }
        return fileNames;
    }

    @Override
    protected void loadItems(String[] fileNames) {
        for (int i = 0; i < fileNames.length; ++i) {
            addAsset(fileNames[i], textures[i].adapt());
        }
    }

}
