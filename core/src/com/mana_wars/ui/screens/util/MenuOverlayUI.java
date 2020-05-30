package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.observer.MenuOverlayUICallbacks;
import com.mana_wars.ui.screens.OverlayUI;
import com.mana_wars.ui.screens.ScreenManager;

import io.reactivex.functions.Consumer;

class MenuOverlayUI implements OverlayUI, MenuOverlayUICallbacks {

    private final NavigationBar navigationBar;
    private final ManaField manaField;
    private final UserLevelField userLevelField;

    MenuOverlayUI(ScreenManager screenManager) {
        manaField = new ManaField(screenManager);
        userLevelField = new UserLevelField(screenManager);
        navigationBar = new NavigationBar(screenManager);
    }

    @Override
    public void init() {
        navigationBar.init();
        manaField.init();
        userLevelField.init();
    }

    @Override
    public void overlay(Stage stage, Skin skin) {
        stage.addActor(navigationBar.build(skin));
        stage.addActor(manaField.build(skin));
        stage.addActor(userLevelField.build(skin));
    }

    @Override
    public Consumer<? super Integer> getUserLevelCallback() {
        return userLevelField::setUserLevel;
    }

    @Override
    public Consumer<? super Integer> getManaAmountCallback() {
        return manaField::setManaAmount;
    }
}
