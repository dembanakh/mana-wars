package com.mana_wars.ui.widgets.base;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface BuildableUI {
    void init();
    Actor build(Skin skin);
}
