package com.mana_wars.ui.overlays;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.base.BuildableUI;

import java.util.Collections;

public class OverlayUIFactory {

    private final MenuOverlayUI menuOverlayUI;
    private final BaseOverlayUI emptyOverlayUI;

    public OverlayUIFactory(final AssetFactory<String, Skin> skinFactory, final ScreenSetter screenSetter) {
        menuOverlayUI = new MenuOverlayUI(skinFactory.getAsset(UIStringConstants.UI_SKIN.MANA_WARS),
                screenSetter);
        emptyOverlayUI = new BaseOverlayUI() {
            @Override
            protected Iterable<BuildableUI> getElements() {
                return Collections.emptyList();
            }
        };
    }

    public MenuOverlayUI getMenuOverlayUI() {
        return menuOverlayUI;
    }

    public BaseOverlayUI getEmptyOverlayUI() {
        return emptyOverlayUI;
    }

}
