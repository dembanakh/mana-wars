package com.mana_wars.ui.widgets.value_field.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.widgets.base.BuildableUI;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN.BACKGROUND_COLOR;

import io.reactivex.functions.Consumer;

public abstract class ValueField<TInitial, TValue> implements BuildableUI, Consumer<TValue> {

    protected Table field;
    private final BACKGROUND_COLOR backgroundColor;

    private final TransformApplier transformApplier;

    protected ValueField(TransformApplier transformApplier) {
        this(BACKGROUND_COLOR.NONE, transformApplier);
    }

    protected ValueField(BACKGROUND_COLOR backgroundColor, TransformApplier transformApplier) {
        this.backgroundColor = backgroundColor;
        this.transformApplier = transformApplier;
    }

    public void setInitialData(TInitial initialData) {

    }

    @Override
    public void init() {
        field = new Table();
        transformApplier.applyTransform(field);
    }

    @Override
    public Actor build(final Skin skin) {
        field.setSkin(skin);
        if (backgroundColor != BACKGROUND_COLOR.NONE)
            field.setBackground(backgroundColor.toString());

        return field;
    }
}
