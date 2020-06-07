package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.OverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

public class LoadingScreen extends BaseScreen {

    private final Skin skin;
    private final OverlayUI overlayUI;

    public LoadingScreen(ScreenSetter screenSetter, FactoryStorage factoryStorage,
                         RepositoryStorage repositoryStorage, OverlayUI overlayUI) {
        super(screenSetter);
        this.overlayUI = overlayUI;

        skin = factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.FREEZING);
    }

    @Override
    protected Skin getSkin() {
        return skin;
    }

    @Override
    protected OverlayUI getOverlayUI() {
        return overlayUI;
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();

        layer.add(new Label("LOADING", skin));

        return layer;
    }

    @Override
    public void show() {
        super.show();
        setScreen(ScreenInstance.GREETING, null);
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

}
