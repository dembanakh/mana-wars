package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class UsernameField implements ValueField<String> {

    private Table field;
    private Label usernameLabel;

    @Override
    public void init() {
        if (field == null) {
            field = new Table();
            field.setTransform(true);

            usernameLabel = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
            usernameLabel.setColor(Color.BLACK);
            usernameLabel.setFontScale(2);

            field.setPosition((SCREEN_WIDTH - usernameLabel.getPrefWidth()) / 2,
                    SCREEN_HEIGHT - usernameLabel.getPrefHeight());
            field.add(usernameLabel).top();
        }
    }

    @Override
    public Actor build(Skin skin) {
        field.setSkin(skin);

        usernameLabel.setStyle(skin.get(Label.LabelStyle.class));

        return field;
    }

    @Override
    public void accept(String username) {
        usernameLabel.setText(username);
    }
}
