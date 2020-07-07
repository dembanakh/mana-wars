package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

final class TextValueField<T> extends ValueField<Void, T> {

    private Label label;

    TextValueField(TransformApplier transformApplier) {
        super(transformApplier);
    }

    TextValueField(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                   TransformApplier transformApplier) {
        super(backgroundColor, transformApplier);
    }

    @Override
    public void init() {
        super.init();
        label = new Label("", UIElementFactory.emptyLabelStyle());
        label.setColor(Color.BLACK);
        label.setFontScale(4);
    }

    @Override
    public Actor build(Skin skin) {
        label.setStyle(skin.get(Label.LabelStyle.class));
        return super.build(skin);
    }

    @Override
    public void accept(T value) {
        label.setText(value.toString());
    }
}
