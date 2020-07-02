package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public class NumberOfAliveEnemiesValueField implements ValueField<Integer> {

    private Table field;

    private Label numberEnemiesAliveLabel;
    private int numberEnemiesAlive;

    @Override
    public void init() {
        field = new Table();
        numberEnemiesAliveLabel = new Label("", UIElementFactory.emptyLabelStyle());
        numberEnemiesAlive = 0;

        field.add(numberEnemiesAliveLabel);
    }

    @Override
    public Actor build(Skin skin) {
        field.setSkin(skin);
        if (backgroundColor != UI_SKIN.BACKGROUND_COLOR.NONE)
            field.setBackground(backgroundColor.toString());

        applyChanges();
        numberEnemiesAliveLabel.setStyle(skin.get(Label.LabelStyle.class));

        return field;
    }

    public void addEnemy() {
        numberEnemiesAlive++;
        applyChanges();
    }

    // onEnemyHealthChanged
    @Override
    public void accept(Integer newHealth) {
        if (newHealth <= 0) {
            numberEnemiesAlive--;
            applyChanges();
        }
    }

    private void applyChanges() {
        if (numberEnemiesAliveLabel != null) numberEnemiesAliveLabel.setText(numberEnemiesAlive);
    }


    private UI_SKIN.BACKGROUND_COLOR backgroundColor = UI_SKIN.BACKGROUND_COLOR.NONE;

    @Override
    public ValueField<Integer> setBackgroundColor(UI_SKIN.BACKGROUND_COLOR backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
}
