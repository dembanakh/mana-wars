package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.screens.ScreenManager;

import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_WIDTH;

class UserLevelField implements BuildableUI {

    private Table field;
    private Label userLevelLabel;

    private final ScreenManager screenManager;

    UserLevelField(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void init() {
        if (field == null) field = new Table();
    }

    @Override
    public Actor build(Skin skin) {
        field.clear();
        field.setSkin(skin);
        field.setTransform(true);
        field.setPosition(0, SCREEN_HEIGHT - 100);
        field.setSize(100, 100);
        field.setBackground("white");

        userLevelLabel = new Label("", skin);
        userLevelLabel.setFillParent(true);
        userLevelLabel.setColor(Color.BLACK);
        userLevelLabel.setFontScale(2);
        field.add(userLevelLabel).center();

        return field;
    }

    void setUserLevel(int userLevel) {
        userLevelLabel.setText(userLevel);
    }

}
