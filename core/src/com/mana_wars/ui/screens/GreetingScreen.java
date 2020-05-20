package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.screens.UIElementsSize.GREETING_SCREEN.*;
import static com.mana_wars.ui.UIStringConstants.*;

class GreetingScreen extends BaseScreen {

    private final Skin skin;

    GreetingScreen(FactoryStorage factoryStorage, ScreenManager screenManager) {
        super(factoryStorage, screenManager);
        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
    }

    @Override
    protected void rebuildStage() {
        // layers
        Table layerBackground = buildBackgroundLayer(skin);
        Table layerForeground = buildForegroundLayer(skin);

        // fill stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(layerBackground);
        stack.add(layerForeground);
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        Label label = new Label(GREETING_SCREEN.LABEL_TEXT, skin);
        label.setFontScale(2);
        layer.add(label).row();
        layer.add(UIElementFactory.getButton(skin, GREETING_SCREEN.BUTTON_TEXT, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onStart();
            }
        })).bottom().padTop(BUTTON_PADDING_TOP).padBottom(BUTTON_PADDING_BOTTOM);

        return layer;
    }

    private void onStart() {
        screenManager.setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
