package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

class ProgressBarValueField<T extends Number, U extends Number> extends ValueField<T, U> {

    private final ProgressBar progressBar;

    private Number actualValue = 0;

    ProgressBarValueField(Skin skin, TransformApplier transformApplier) {
        super(skin, transformApplier);
        this.progressBar = new ProgressBar(0, 100, 1, false, skin);
        init();
    }

    private void init() {
        progressBar.getStyle().background.setMinHeight(50);
        progressBar.getStyle().knobBefore.setMinHeight(50);
        field.add(progressBar).top().row();
    }

    @Override
    public void setInitialData(T maximumValue) {
        progressBar.setRange(0, maximumValue.floatValue());
        progressBar.setValue(actualValue.floatValue());
    }

    @Override
    public void accept(U value) {
        this.actualValue = value;
        progressBar.setValue(value.floatValue());
    }
}
