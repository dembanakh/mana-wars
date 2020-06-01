package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class ManaAmountField implements BuildableUI {

    private Table field;
    private Label manaAmountLabel;

    @Override
    public void init() {
        if (field == null) {
            field = new Table();
            field.setTransform(true);
            field.setPosition(SCREEN_WIDTH - 100, SCREEN_HEIGHT - 50);
            field.setSize(100, 50);

            manaAmountLabel = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
            manaAmountLabel.setFillParent(true);
            manaAmountLabel.setColor(Color.BLACK);
            manaAmountLabel.setFontScale(2);
            field.add(manaAmountLabel).center();
        }
    }

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);
        field.setBackground("white");

        manaAmountLabel.setStyle(skin.get(Label.LabelStyle.class));

        return field;
    }

    public void setManaAmount(final int manaAmount) {
        manaAmountLabel.setText(manaAmount);
    }

}
