package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class UsernameField extends ValueField<String> {

    @Override
    public void init() {
        if (field == null) {
            field = new Table();
            field.setTransform(true);

            label = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
            label.setColor(Color.BLACK);
            label.setFontScale(2);

            field.setPosition((SCREEN_WIDTH - label.getPrefWidth()) / 2,
                    SCREEN_HEIGHT - label.getPrefHeight());
            field.add(label).top();
        }
    }

}
