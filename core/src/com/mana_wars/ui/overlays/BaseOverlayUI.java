package com.mana_wars.ui.overlays;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.widgets.base.BuildableUI;

public abstract class BaseOverlayUI {

    public void overlay(Stage stage) {
        for (BuildableUI element : getElements())
            stage.addActor(element.build());
    }

    protected abstract Iterable<BuildableUI> getElements();
}
