package com.mana_wars.ui.overlays;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.screens.util.BuildableUI;
import com.mana_wars.ui.screens.util.ValueField;

import java.util.ArrayList;
import java.util.List;

public abstract class OverlayUI {

    public void init() {
        for (BuildableUI element : getElements())
            element.init();
    }

    public void overlay(Stage stage, Skin skin) {
        for (BuildableUI element : getElements())
            stage.addActor(element.build(skin));
    }

    protected abstract Iterable<BuildableUI> getElements();
}
