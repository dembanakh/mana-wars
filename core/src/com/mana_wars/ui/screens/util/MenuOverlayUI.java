package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.screens.OverlayUI;
import com.mana_wars.ui.screens.ScreenManager;

import java.util.Arrays;
import java.util.List;

class MenuOverlayUI implements OverlayUI {

    private final List<BuildableUI> elements;

    MenuOverlayUI(ScreenManager screenManager) {
        elements = Arrays.asList(new NavigationBar(screenManager),
                                new ManaField(screenManager),
                                new UserLevelField(screenManager));
    }

    @Override
    public void init() {
        for (BuildableUI element : elements)
            element.init();
    }

    @Override
    public void overlay(Stage stage, Skin skin) {
        for (BuildableUI element : elements)
            stage.addActor(element.build(skin));
    }

}
