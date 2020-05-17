package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.factory.AssetFactory;

public class ScreenManager {

    private ScreenHandler handler;

    private final AssetFactory<Integer, TextureRegion> skillIconFactory;
    private final AssetFactory<String, Skin> skinFactory;

    private NavigationBar navigationBar;

    public ScreenManager(ScreenHandler handler) {
        this.handler = handler;
        skillIconFactory = new AssetFactory<Integer, TextureRegion>("Skills_icons") {
            @Override
            public void loadItems() {
                //TODO: remove hardcoded file extension
                final TextureAtlas textureAtlas =
                        new TextureAtlas(String.format("%s/%s.pack", fileNames[0], fileNames[0]));
                final String regionName = "image_part";
                for (TextureAtlas.AtlasRegion region : textureAtlas.findRegions(regionName)) {
                    items.put(region.index, region);
                }
            }
        };
        skinFactory = new AssetFactory<String, Skin>("freezing") {
            @Override
            public void loadItems() {
                for (String fileName : fileNames) {
                    //TODO: remove hardcoded file extension
                    final String path = String.format("skins/%s/skin/%s-ui.json", fileName, fileName);
                    items.put(fileName, new Skin(Gdx.files.internal(path)));
                }
            }
        };
        navigationBar = new NavigationBar(this);
    }

    public void start() {
        skillIconFactory.loadItems();
        skinFactory.loadItems();
        //TODO add check isFirstOpen
        handler.setScreen(ScreenInstance.GREETING.getScreen());
    }

    public void dispose() {
        for (ScreenInstance screenInstance : ScreenInstance.values()) {
            screenInstance.getScreen().dispose();
        }
    }

    AssetFactory<Integer, TextureRegion> getSkillIconFactory() {
        return skillIconFactory;
    }

    AssetFactory<String, Skin> getSkinFactory() {
        return skinFactory;
    }

    NavigationBar getNavigationBar() {
        return navigationBar;
    }

    enum ScreenInstance {
        GREETING(new GreetingScreen()),
        MAIN_MENU(new MainMenuScreen()),
        SKILLS(new SkillsScreen());

        private final BaseScreen screen;
        ScreenInstance(BaseScreen screen) {
            this.screen = screen;
        }

        public BaseScreen getScreen() { return screen; }
    }

    void setScreen(ScreenInstance screenInstance) {
        handler.setScreen(screenInstance.getScreen());
    }

}
