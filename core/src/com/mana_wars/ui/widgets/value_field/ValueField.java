package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.widgets.BuildableUI;

import io.reactivex.functions.Consumer;

public abstract class ValueField<T> implements BuildableUI, Consumer<T> {

    Table field;
    Label label;

    T lastValue = null;

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);

        label.setStyle(skin.get(Label.LabelStyle.class));

        return field;
    }

    synchronized void setLabel(Label targetLabel) {
        label = targetLabel;
        if (lastValue != null) label.setText(lastValue.toString());
    }

    @Override
    public synchronized void accept(T value) {
        lastValue = value;
        if (label != null)
            label.setText(value.toString());
    }

}
