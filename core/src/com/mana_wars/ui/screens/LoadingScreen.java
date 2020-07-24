package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.presentation.presenters.BasePresenter;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;

import java.util.Map;

public final class LoadingScreen extends BaseScreen<BaseOverlayUI, BasePresenter> {

    private final DatabaseUpdater updater;
    private final LocalizedStringFactory localizedStringFactory;

    public LoadingScreen(ScreenSetter screenSetter, FactoryStorage factoryStorage,
                         BaseOverlayUI overlayUI, DatabaseUpdater updater) {
        super(screenSetter,
                factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.FREEZING),
                overlayUI);
        this.updater = updater;
        this.localizedStringFactory = factoryStorage.getLocalizedStringFactory();
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();

        layer.add(new Label(localizedStringFactory.get(UIStringConstants.LOADING_SCREEN.LOADING_KEY), skin));

        return layer;
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        updater.checkUpdate(() -> {
            Gdx.app.postRunnable(() -> {
                setScreen(ScreenInstance.GREETING, null);
            });
        }, Gdx.app::exit);
        return super.reInit(arguments);
    }

}
