package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class ManaAmountField extends ValueField<Integer> {

    @Override
    public void init() {
        if (field == null) {
            field = new Table();
            field.setTransform(true);
            field.setPosition(SCREEN_WIDTH - 100, SCREEN_HEIGHT - 50);
            field.setSize(100, 50);

            label = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
            label.setFillParent(true);
            label.setColor(Color.BLACK);
            label.setFontScale(2);
            field.add(label).center();
        }
    }

    @Override
    public Actor build(final Skin skin) {
        Actor result = super.build(skin);
        field.setBackground("white");
        return result;
    }

}
