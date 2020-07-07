package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.base.BuildableUI;

import java.util.Collections;

public class OverlayUIFactory {

    private final MenuOverlayUI menuOverlayUI;
    private final BaseOverlayUI emptyOverlayUI;

    public OverlayUIFactory(final ScreenSetter screenSetter) {
        menuOverlayUI = new MenuOverlayUI(screenSetter);
        emptyOverlayUI = new BaseOverlayUI() {
            @Override
            protected Iterable<BuildableUI> getElements() {
                return Collections.emptyList();
            }
        };
        init();
    }

    private void init() {
        menuOverlayUI.init();
        emptyOverlayUI.init();
    }

    public MenuOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public BaseOverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
