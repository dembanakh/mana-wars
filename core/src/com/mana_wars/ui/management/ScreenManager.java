package com.mana_wars.ui.management;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.user.User;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.ImageFactory;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.factory.RarityFrameFactory;
import com.mana_wars.ui.factory.SkillIconFactory;
import com.mana_wars.ui.factory.SkinFactory;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.textures.AdaptiveTexture;
import com.mana_wars.ui.textures.FillScreenTexture;
import com.mana_wars.ui.textures.FixedSizeMirroredTexture;
import com.mana_wars.ui.textures.FixedSizeTexture;

import java.util.Locale;
import java.util.Map;

import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public class ScreenManager implements FactoryStorage, ScreenSetter {

    private final ScreenHandler handler;

    private final AssetFactory<Integer, TextureRegion> skillIconFactory;
    private final AssetFactory<String, Skin> skinFactory;
    private final AssetFactory<Rarity, TextureRegion> rarityFrameFactory;
    private final AssetFactory<String, TextureRegion> pngImageFactory;
    private final LocalizedStringFactory localizedStringFactory;

    public ScreenManager(final ScreenHandler handler,
                         final User user,
                         final RepositoryStorage repositoryStorage,
                         final DatabaseUpdater databaseUpdater) {
        this.handler = handler;
        skillIconFactory = new SkillIconFactory(1, 112).build();
        skinFactory = new SkinFactory(UI_SKIN.FREEZING, UI_SKIN.MANA_WARS).build();
        rarityFrameFactory = new RarityFrameFactory().build();
        pngImageFactory = new ImageFactory(
                new FixedSizeTexture("white", AdaptiveTexture.ImageFormat.PNG),
                new FixedSizeTexture("player", AdaptiveTexture.ImageFormat.PNG),
                new FixedSizeTexture("enemy", AdaptiveTexture.ImageFormat.PNG),
                new FixedSizeMirroredTexture("enemy", AdaptiveTexture.ImageFormat.PNG),
                new FillScreenTexture("bg1", AdaptiveTexture.ImageFormat.PNG),
                new FillScreenTexture("bg2", AdaptiveTexture.ImageFormat.PNG))
                .build();
        localizedStringFactory = new LocalizedStringFactory("localization/Bundle",
                new Locale("en", "US"));
        ScreenInstance.init(user, this, this, repositoryStorage, databaseUpdater);
        start();
    }

    private void start() {
        setScreen(ScreenInstance.LOADING, null);
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
    public AssetFactory<String, TextureRegion> getImageFactory() {
        return pngImageFactory;
    }

    @Override
    public LocalizedStringFactory getLocalizedStringFactory() {
        return localizedStringFactory;
    }

    @Override
    public void setScreen(final ScreenInstance screenInstance, Map<String, Object> arguments) {
        handler.setScreen(screenInstance.getScreen(arguments));
    }

}
