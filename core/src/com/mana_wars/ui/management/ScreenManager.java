package com.mana_wars.ui.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.user.User;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.overlays.OverlayUIFactory;
import com.mana_wars.ui.screens.LoadingScreen;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

import java.util.Map;

import static com.mana_wars.ui.UIStringConstants.*;

public class ScreenManager implements FactoryStorage, ScreenSetter {

    private final ScreenHandler handler;

    private final AssetFactory<Integer, TextureRegion> skillIconFactory;
    private final AssetFactory<String, Skin> skinFactory;
    private final AssetFactory<Rarity, TextureRegion> rarityFrameFactory;

    private final OverlayUIFactory overlayUIFactory;

    public ScreenManager(final ScreenHandler handler) {
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
                    Skin skin = new Skin(Gdx.files.internal(path));
                    items.put(fileName, skin);
                    skin.getFont("font").getData().setScale(1.5f);
                }
            }
        };
        rarityFrameFactory = new AssetFactory<Rarity, TextureRegion>(SKILLS_FRAMES_FILENAME) {
            @Override
            public void loadItems() {
                final TextureAtlas textureAtlas =
                        new TextureAtlas(String.format(TEXTURE_ATLAS_FORMAT, fileNames[0]));
                for (TextureAtlas.AtlasRegion region : textureAtlas.getRegions()) {
                    items.put(Rarity.valueOf(region.name), region);
                }
            }
        };
        this.overlayUIFactory = new OverlayUIFactory(this, this);
    }

    public void start(final User user, final RepositoryStorage repositoryStorage,
                      final DatabaseUpdater databaseUpdater) {
        skillIconFactory.loadItems();
        skinFactory.loadItems();
        rarityFrameFactory.loadItems();
        overlayUIFactory.init();
        ScreenInstance.init(user, this, this, repositoryStorage, overlayUIFactory);
        handler.setScreen(new LoadingScreen(this, this,
                overlayUIFactory.getEmptyOverlayUI(), databaseUpdater).reInit(null));
    }

    public void dispose() {
        ScreenInstance.dispose();
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
    public AssetFactory<Rarity, TextureRegion> getRarityFrameFactory() {
        return rarityFrameFactory;
    }

    @Override
    public void setScreen(final ScreenInstance screenInstance, Map<String, Object> arguments) {
        handler.setScreen(screenInstance.getScreen(arguments));
    }

}
