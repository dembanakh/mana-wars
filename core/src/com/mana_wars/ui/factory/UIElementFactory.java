package com.mana_wars.ui.factory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public final class UIElementFactory {

    public static TextButton getButton(TextButton.TextButtonStyle style, String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, style);
        button.addListener(eventListener);
        return button;
    }

    public static TextButton getButton(Skin skin, String label, ChangeListener eventListener) {
        return getButton(skin.get(TextButton.TextButtonStyle.class), label, eventListener);
    }

    private UIElementFactory() {}

}
