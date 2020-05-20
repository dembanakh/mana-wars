package com.mana_wars.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.screens.FactoryStorage;
import com.mana_wars.ui.screens.NavigationBar;
import com.mana_wars.ui.screens.ScreenManager;

import static com.mana_wars.ui.UIStringConstants.*;

public class ScreenManagerImpl implements FactoryStorage, ScreenManager {

    private ScreenHandler handler;

    private final AssetFactory<Integer, TextureRegion> skillIconFactory;
    private final AssetFactory<String, Skin> skinFactory;

    private final NavigationBar navigationBar;

    public ScreenManagerImpl(ScreenHandler handler) {
        this.handler = handler;
        skillIconFactory = new AssetFactory<Integer, TextureRegion>(SKILLS_ICONS_FILENAME) {
            @Override
            public void loadItems() {
                final TextureAtlas textureAtlas =
                        new TextureAtlas(String.format(TEXTURE_ATLAS_FORMAT, fileNames[0]));
                for (TextureAtlas.AtlasRegion region : textureAtlas.findRegions(REGION_NAME)) {
                    items.put(region.index, region);
                }
            }
        };
        skinFactory = new AssetFactory<String, Skin>(UI_SKIN.FREEZING) {
            @Override
            public void loadItems() {
                for (String fileName : fileNames) {
                    final String path = String.format(UI_SKIN.FORMAT, fileName);
                    items.put(fileName, new Skin(Gdx.files.internal(path)));
                }
            }
        };
        navigationBar = NavigationBar.create(this);
    }

    public void start(FirstOpenFlag firstOpenFlag) {
        skillIconFactory.loadItems();
        skinFactory.loadItems();
        navigationBar.start();
        if (firstOpenFlag.getIsFirstOpen()) {
            firstOpenFlag.setIsFirstOpen(false);
            handler.setScreen(ScreenInstance.GREETING.getScreen());
        } else {
            handler.setScreen(ScreenInstance.MAIN_MENU.getScreen());
        }
    }

    public void dispose() {
        for (ScreenInstance screenInstance : ScreenInstance.values()) {
            screenInstance.dispose();
        }
    }

    @Override
    public AssetFactory<Integer, TextureRegion> getSkillIconFactory() {
        return skillIconFactory;
    }

    @Override
    public AssetFactory<String, Skin> getSkinFactory() {
        return skinFactory;
    }

    @Override
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    @Override
    public void setScreen(ScreenInstance screenInstance) {
        handler.setScreen(screenInstance.getScreen());
    }

}
