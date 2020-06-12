package com.mana_wars.ui.overlays;

import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.BuildableUI;

import java.util.Collections;

public class OverlayUIFactory {

    private final MenuBaseOverlayUI menuOverlayUI;
    private final BattleBaseOverlayUI battleOverlayUI;
    private final BaseOverlayUI emptyOverlayUI;

    public OverlayUIFactory(final ScreenSetter screenSetter, final FactoryStorage factoryStorage) {
        menuOverlayUI = new MenuBaseOverlayUI(screenSetter);
        battleOverlayUI = new BattleBaseOverlayUI(screenSetter, factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory());
        emptyOverlayUI = new BaseOverlayUI() {
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

    public MenuBaseOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public BattleBaseOverlayUI getBattleOverlayUI() {
        return battleOverlayUI;
    }

    public BaseOverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
