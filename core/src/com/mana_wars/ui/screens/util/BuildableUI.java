package com.mana_wars.ui.screens.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

interface BuildableUI {
    void init();
    Actor build(Skin skin);
}
