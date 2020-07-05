package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;

public class TextValueField<T> implements ValueField<T> {

    private Label label;

    @Override
    public void init() {
        label = new Label("", UIElementFactory.emptyLabelStyle());
        label.setColor(Color.BLACK);
        label.setFontScale(4);
    }

    @Override
    public Actor build(Skin skin) {
        label.setStyle(skin.get(Label.LabelStyle.class));
        if (backgroundColor != UIStringConstants.UI_SKIN.BACKGROUND_COLOR.NONE)
            label.getStyle().background = skin.getDrawable(backgroundColor.toString());
        return label;
    }

    @Override
    public synchronized void accept(T value) {
        label.setText(value.toString());
    }

    private UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor = UIStringConstants.UI_SKIN.BACKGROUND_COLOR.NONE;

    @Override
    public ValueField<T> setBackgroundColor(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
}
