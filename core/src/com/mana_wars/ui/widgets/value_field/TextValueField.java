package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

final class TextValueField<T> extends ValueField<Void, T> {

    private final Label label;

    TextValueField(Skin skin, TransformApplier transformApplier) {
        super(skin, transformApplier);
        label = new Label("", skin);
        init();
    }

    TextValueField(Skin skin, UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                   TransformApplier transformApplier) {
        super(skin, backgroundColor, transformApplier);
        label = new Label("", skin);
        init();
    }

    private void init() {
        label.setColor(Color.BLACK);
        label.setFontScale(4);
        field.add(label).top().row();
    }

    @Override
    public void accept(T value) {
        label.setText(value.toString());
    }
}
