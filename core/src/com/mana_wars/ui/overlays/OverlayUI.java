package com.mana_wars.ui.overlays;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.screens.util.BuildableUI;

import java.util.ArrayList;
import java.util.List;

public class OverlayUI {

    protected final List<BuildableUI> elements;

    public OverlayUI() {
        this.elements = new ArrayList<>();
    }

    public void overlay(Stage stage, Skin skin) {
        for (BuildableUI element : elements)
            stage.addActor(element.build(skin));
    }
}
