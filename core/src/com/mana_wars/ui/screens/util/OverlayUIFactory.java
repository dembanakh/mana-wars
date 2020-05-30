package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.screens.OverlayUI;
import com.mana_wars.ui.screens.ScreenManager;

public class OverlayUIFactory {

    private final MenuOverlayUI menuOverlayUI;
    private final OverlayUI emptyOverlayUI;

    public OverlayUIFactory(ScreenManager screenManager) {
        menuOverlayUI = new MenuOverlayUI(screenManager);
        emptyOverlayUI = new OverlayUI() {
            @Override
            public void init() {

            }

            @Override
            public void overlay(Stage stage, Skin skin) {

            }
        };
    }

    public void init() {
        menuOverlayUI.init();
    }

    public MenuOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public OverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
