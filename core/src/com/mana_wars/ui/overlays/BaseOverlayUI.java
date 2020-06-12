package com.mana_wars.ui.overlays;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.widgets.BuildableUI;

public abstract class BaseOverlayUI {

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
