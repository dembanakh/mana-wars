package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_WIDTH;

public class HealthField implements BuildableUI{
    private Table field;
    private Label userHealthLabel;


    private final int widthOffset;
    private final int heightOffset;

    public HealthField(int widthOffset, int heightOffset){
        this.widthOffset = widthOffset;
        this.heightOffset = heightOffset;
    }

    @Override
    public void init() {
        if (field == null) {
            field = new Table();
            field.setTransform(true);

            field.setPosition(SCREEN_WIDTH - widthOffset, SCREEN_HEIGHT - heightOffset);

            field.setSize(100, 50);

            userHealthLabel = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
            userHealthLabel.setFillParent(true);
            userHealthLabel.setColor(Color.BLACK);
            userHealthLabel.setFontScale(2);
            field.add(userHealthLabel).center();
        }
    }

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);
        field.setBackground("white");

        userHealthLabel.setStyle(skin.get(Label.LabelStyle.class));

        return field;
    }

    public void setHealth(final int health) {
        userHealthLabel.setText(health);
    }
}
