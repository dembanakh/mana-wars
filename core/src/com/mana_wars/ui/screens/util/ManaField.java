package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.screens.ScreenManager;

import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_WIDTH;

class ManaField implements BuildableUI {

    private Table field;
    private Label manaAmountLabel;

    private final ScreenManager screenManager;

    ManaField(ScreenManager screenManager) {
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
        field.setPosition(SCREEN_WIDTH - 100, SCREEN_HEIGHT - 100);
        field.setSize(100, 100);
        field.setBackground("white");

        manaAmountLabel = new Label("", skin);
        manaAmountLabel.setFillParent(true);
        manaAmountLabel.setColor(Color.BLACK);
        manaAmountLabel.setFontScale(2);
        field.add(manaAmountLabel).center();

        return field;
    }

    void setManaAmount(int manaAmount) {
        manaAmountLabel.setText(manaAmount);
    }

}
