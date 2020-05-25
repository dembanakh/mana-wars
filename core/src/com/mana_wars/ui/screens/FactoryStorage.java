package com.mana_wars.ui.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.ui.factory.AssetFactory;

public interface FactoryStorage {

    AssetFactory<Integer, TextureRegion> getSkillIconFactory();
    AssetFactory<String, Skin> getSkinFactory();
    AssetFactory<Rarity, TextureRegion> getRarityFrameFactory();

}
