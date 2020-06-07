package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

public class BarValueField extends ValueField<Integer> {

    private Label label;
    private ProgressBar bar;

    @Override
    public void init() {
        super.init();
        Stack stack = new Stack();

        bar = new ProgressBar(0, 100, 1, false,
                new ProgressBar.ProgressBarStyle());
        stack.add(bar);

        label = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
        label.setFillParent(true);
        label.setColor(Color.BLACK);
        label.setFontScale(2);
        stack.add(label);

        addActor(stack);
    }

    @Override
    public Actor build(final Skin skin) {
        Actor field = super.build(skin);
        label.setStyle(skin.get(Label.LabelStyle.class));
        bar.setStyle(skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        return field;
    }

    @Override
    public synchronized void accept(Integer value) {
        label.setText(value);
        bar.setValue(value);
    }

}
