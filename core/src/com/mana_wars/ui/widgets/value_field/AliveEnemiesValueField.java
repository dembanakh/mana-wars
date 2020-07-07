package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

final class AliveEnemiesValueField extends ValueField<Integer, Integer> {

    private final Label label;

    private int aliveEnemiesNumber;

    AliveEnemiesValueField(final Skin skin, final TransformApplier transformApplier) {
        super(skin, transformApplier);
        this.label = new Label("", skin);
        init();
    }

    AliveEnemiesValueField(final Skin skin, UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                   TransformApplier transformApplier) {
        super(skin, backgroundColor, transformApplier);
        this.label = new Label("", skin);
        init();
    }

    private void init() {
        label.setColor(Color.BLACK);
        label.setFontScale(4);
        field.add(label).top().row();
    }

    @Override
    public void setInitialData(Integer initialEnemiesNumber) {
        aliveEnemiesNumber = initialEnemiesNumber;
        applyChanges();
    }

    @Override
    public void accept(Integer newHealth) {
        if (newHealth <= 0) {
            aliveEnemiesNumber--;
            applyChanges();
        }
    }

    private void applyChanges() {
        label.setText(aliveEnemiesNumber);
    }
}
