package com.mana_wars.ui.widgets.value_field.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.widgets.base.BuildableUI;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN.BACKGROUND_COLOR;

import io.reactivex.functions.Consumer;

public abstract class ValueField<TInitial, TValue> implements BuildableUI, Consumer<TValue> {

    protected final Table field;

    protected ValueField(Skin skin, TransformApplier transformApplier) {
        this(skin, BACKGROUND_COLOR.NONE, transformApplier);
    }

    protected ValueField(Skin skin, BACKGROUND_COLOR backgroundColor, TransformApplier transformApplier) {
        this.field = new Table(skin);
        if (backgroundColor != BACKGROUND_COLOR.NONE)
            field.setBackground(backgroundColor.toString());
        transformApplier.applyTransform(field);
    }

    public void setInitialData(TInitial initialData) {

    }

    @Override
    public Actor build() {
        return field;
    }
}
