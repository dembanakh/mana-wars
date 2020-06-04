package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;

import java.util.Collections;

public class OverlayUIFactory {

    private final MenuOverlayUI menuOverlayUI;
    private final BattleOverlayUI battleOverlayUI;
    private final OverlayUI emptyOverlayUI;

    public OverlayUIFactory(ScreenSetter screenSetter) {
        menuOverlayUI = new MenuOverlayUI(screenSetter);
        battleOverlayUI = new BattleOverlayUI(screenSetter);
        emptyOverlayUI = new OverlayUI() {
            @Override
            protected Iterable<BuildableUI> getElements() {
                return Collections.emptyList();
            }
        };
    }

    public void init() {
        menuOverlayUI.init();
        battleOverlayUI.init();
        emptyOverlayUI.init();
    }

    public MenuOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public BattleOverlayUI getBattleOverlayUI() {
        return battleOverlayUI;
    }

    public OverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
