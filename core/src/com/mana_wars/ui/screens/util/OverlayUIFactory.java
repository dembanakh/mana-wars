package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.screens.OverlayUI;
import com.mana_wars.ui.screens.ScreenManager;

import java.util.HashMap;
import java.util.Map;

public class OverlayUIFactory {

    private final Map<ScreenManager.ScreenInstance, OverlayUI> overlayUIMap;

    public OverlayUIFactory(ScreenManager screenManager) {
        overlayUIMap = new HashMap<>();
        OverlayUI menuOverlayUI = new MenuOverlayUI(screenManager);
        OverlayUI emptyOverlayUI = new OverlayUI() {
            @Override
            public void init() {

            }

            @Override
            public void overlay(Stage stage, Skin skin) {

            }
        };
        overlayUIMap.put(ScreenManager.ScreenInstance.GREETING, emptyOverlayUI);
        overlayUIMap.put(ScreenManager.ScreenInstance.MAIN_MENU, menuOverlayUI);
        overlayUIMap.put(ScreenManager.ScreenInstance.SKILLS, menuOverlayUI);
        overlayUIMap.put(ScreenManager.ScreenInstance.TEST_BATTLE, emptyOverlayUI);
    }

    public void init() {
        for (OverlayUI overlayUI : overlayUIMap.values())
            overlayUI.init();
    }

    public OverlayUI getOverlayUI(ScreenManager.ScreenInstance instance) {
        return overlayUIMap.get(instance);
    }

}
