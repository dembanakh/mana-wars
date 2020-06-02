package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.screens.util.BuildableUI;

import java.util.Collections;

public class OverlayUIFactory {

    private final MenuOverlayUI menuOverlayUI;
    private final OverlayUI emptyOverlayUI;

    public OverlayUIFactory(ScreenManager screenManager) {
        menuOverlayUI = new MenuOverlayUI(screenManager);
        emptyOverlayUI = new OverlayUI() {
            @Override
            protected Iterable<BuildableUI> getElements() {
                return Collections.emptyList();
            }
        };
    }

    public void init() {
        menuOverlayUI.init();
        emptyOverlayUI.init();
    }

    public MenuOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public OverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
