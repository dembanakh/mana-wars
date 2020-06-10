package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.OverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;

import java.util.Map;

public class LoadingScreen extends BaseScreen {

    private final Skin skin;
    private final OverlayUI overlayUI;
    private final DatabaseUpdater updater;

    public LoadingScreen(ScreenSetter screenSetter, FactoryStorage factoryStorage,
                         OverlayUI overlayUI, DatabaseUpdater updater) {
        super(screenSetter);
        this.overlayUI = overlayUI;
        this.updater = updater;
        this.skin = factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.FREEZING);
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
    public BaseScreen reInit(Map<String, Object> arguments) {
        System.out.println("her1");
        //TODO activate loading animation
        updater.checkUpdate(()->{
            Gdx.app.postRunnable(()->{
                setScreen(ScreenInstance.GREETING, null);
            });
        });
        return super.reInit(arguments);
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

}
