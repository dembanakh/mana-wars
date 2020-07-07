package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mana_wars.model.entity.base.Rarity;

import static com.mana_wars.ui.UIStringConstants.SKILLS_FRAMES_FILENAME;
import static com.mana_wars.ui.UIStringConstants.TEXTURE_ATLAS_FORMAT;

public class RarityFrameFactory extends AssetFactoryBuilder<Rarity, TextureRegion, Rarity> {

    private final TextureAtlas atlas;

    public RarityFrameFactory() {
        super(Rarity.values());
        atlas = new TextureAtlas(String.format(TEXTURE_ATLAS_FORMAT, SKILLS_FRAMES_FILENAME));
    }

    @Override
    protected Rarity key(Rarity rarity) {
        return rarity;
    }

    @Override
    protected TextureRegion loadAsset(Rarity rarity) {
        return atlas.findRegion(rarity.toString());
    }
}
