package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface OverlayUI {
    void init();
    void overlay(Stage stage, Skin skin);
}
