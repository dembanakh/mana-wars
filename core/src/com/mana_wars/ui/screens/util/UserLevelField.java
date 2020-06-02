package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;

public class UserLevelField implements ValueField<Integer> {

    private Table field;
    private Label userLevelLabel;

    @Override
    public void init() {
        if (field == null) {
            field = new Table();
            field.setTransform(true);
            field.setPosition(0, SCREEN_HEIGHT - 50);
            field.setSize(100, 50);

            userLevelLabel = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
            userLevelLabel.setFillParent(true);
            userLevelLabel.setColor(Color.BLACK);
            userLevelLabel.setFontScale(2);
            field.add(userLevelLabel).center();
        }
    }

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);
        field.setBackground("white");

        userLevelLabel.setStyle(skin.get(Label.LabelStyle.class));

        return field;
    }

    @Override
    public void accept(Integer userLevel) {
        userLevelLabel.setText(userLevel);
    }
}
