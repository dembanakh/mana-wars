package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

final class AliveEnemiesValueField extends ValueField<Integer, Integer> {

    private Label label;

    private int aliveEnemiesNumber;

    AliveEnemiesValueField(TransformApplier transformApplier) {
        super(transformApplier);
    }

    AliveEnemiesValueField(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
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
