package com.mana_wars.ui.screens.util;

import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.ui.screens.OverlayUI;
import com.mana_wars.ui.screens.ScreenManager;

public class OverlayUIFactory {

    private final MenuOverlayUI menuOverlayUI;
    private final OverlayUI emptyOverlayUI;

    public OverlayUIFactory(ScreenManager screenManager) {
        menuOverlayUI = new MenuOverlayUI(screenManager);
        emptyOverlayUI = new OverlayUI();
    }

    public void init(LocalUserDataRepository localUserDataRepository) {
        menuOverlayUI.init(localUserDataRepository, localUserDataRepository, localUserDataRepository);
    }

    public MenuOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public OverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
