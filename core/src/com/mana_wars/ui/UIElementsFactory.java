package com.mana_wars.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public final class UIElementsFactory {

    public static TextButton getButton(Skin skin, String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, skin);
        button.addListener(eventListener);
        return button;
    }

    private UIElementsFactory() {}

}
