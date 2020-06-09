package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TextValueField<T> extends ValueField<T> {

    private Label label;

    @Override
    public void init() {
        super.init();
        label = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
        label.setFillParent(true);
        label.setColor(Color.BLACK);
        label.setFontScale(4);
        addActor(label);
    }

    @Override
    public Actor build(final Skin skin) {
        Actor field = super.build(skin);
        label.setStyle(skin.get(Label.LabelStyle.class));
        return field;
    }

    @Override
    public synchronized void accept(T value) {
        label.setText(value.toString());
    }

}
