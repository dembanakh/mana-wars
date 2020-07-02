package com.mana_wars.ui.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.user.User;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.ImageFactory;
import com.mana_wars.ui.overlays.OverlayUIFactory;
import com.mana_wars.ui.screens.LoadingScreen;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.textures.AdaptiveTexture;
import com.mana_wars.ui.textures.FillScreenTexture;
import com.mana_wars.ui.textures.FixedSizeTexture;

import java.util.Map;

import static com.mana_wars.ui.UIStringConstants.REGION_NAME;
import static com.mana_wars.ui.UIStringConstants.SKILLS_FRAMES_FILENAME;
import static com.mana_wars.ui.UIStringConstants.SKILLS_ICONS_FILENAME;
import static com.mana_wars.ui.UIStringConstants.TEXTURE_ATLAS_FORMAT;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public class ScreenManager implements FactoryStorage, ScreenSetter {

    private final ScreenHandler handler;

    private final AssetFactory<Integer, TextureRegion> skillIconFactory;
    private final AssetFactory<String, Skin> skinFactory;
    private final AssetFactory<Rarity, TextureRegion> rarityFrameFactory;
    private final AssetFactory<String, Texture> pngImageFactory;

    private final OverlayUIFactory overlayUIFactory;

    public ScreenManager(final ScreenHandler handler) {
        this.handler = handler;
        skillIconFactory = new AssetFactory<Integer, TextureRegion>(SKILLS_ICONS_FILENAME) {
            @Override
            public void loadItems(String[] fileNames) {
                final TextureAtlas textureAtlas =
                        new TextureAtlas(String.format(TEXTURE_ATLAS_FORMAT, fileNames[0]));
                for (TextureAtlas.AtlasRegion region : textureAtlas.findRegions(REGION_NAME)) {
                    addAsset(region.index, region);
                }
            }
        };
        skinFactory = new AssetFactory<String, Skin>(UI_SKIN.FREEZING, UI_SKIN.MANA_WARS) {
            @Override
            public void loadItems(String[] fileNames) {
                for (String fileName : fileNames) {
                    final String path = String.format(UI_SKIN.FORMAT, fileName);
                    Skin skin = new Skin(Gdx.files.internal(path));
                    addAsset(fileName, skin);
                    for (BitmapFont font : skin.getAll(BitmapFont.class).values()) {
                        font.getData().setScale(1.5f);
                    }
                    //skin.getFont("font").getData().setScale(1.5f);
                }
            }
        };
        rarityFrameFactory = new AssetFactory<Rarity, TextureRegion>(SKILLS_FRAMES_FILENAME) {
            @Override
            public void loadItems(String[] fileNames) {
                final TextureAtlas textureAtlas =
                        new TextureAtlas(String.format(TEXTURE_ATLAS_FORMAT, fileNames[0]));
                for (TextureAtlas.AtlasRegion region : textureAtlas.getRegions()) {
                    addAsset(Rarity.valueOf(region.name), region);
                }
            }
        };
        pngImageFactory = new ImageFactory(
                new FixedSizeTexture("white", AdaptiveTexture.ImageFormat.PNG),
                new FixedSizeTexture("player", AdaptiveTexture.ImageFormat.PNG),
                new FixedSizeTexture("enemy", AdaptiveTexture.ImageFormat.PNG),
                new FillScreenTexture("bg1", AdaptiveTexture.ImageFormat.PNG),
                new FillScreenTexture("bg2", AdaptiveTexture.ImageFormat.PNG));
        this.overlayUIFactory = new OverlayUIFactory(this, this);
    }

    public void start(final User user, final RepositoryStorage repositoryStorage,
                      final DatabaseUpdater databaseUpdater) {
        skillIconFactory.loadItems();
        skinFactory.loadItems();
        rarityFrameFactory.loadItems();
        pngImageFactory.loadItems();
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
    public AssetFactory<String, Texture> getImageFactory() {
        return pngImageFactory;
    }

    @Override
    public void setScreen(final ScreenInstance screenInstance, Map<String, Object> arguments) {
        handler.setScreen(screenInstance.getScreen(arguments));
    }

}
