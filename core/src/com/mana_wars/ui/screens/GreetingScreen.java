package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.mana_wars.model.interactor.GreetingInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.presentation.presenters.GreetingPresenter;
import com.mana_wars.presentation.view.GreetingView;
import com.mana_wars.ui.factory.UIElementFactory;

import java.awt.event.FocusEvent;

import static com.mana_wars.ui.screens.UIElementsSize.GREETING_SCREEN.*;
import static com.mana_wars.ui.UIStringConstants.*;

class GreetingScreen extends BaseScreen implements GreetingView {

    private final Skin skin;

    private final GreetingPresenter presenter;

    GreetingScreen(FactoryStorage factoryStorage, ScreenManager screenManager,
                   LocalUserDataRepository localUserDataRepository, DatabaseRepository databaseRepository) {
        super(factoryStorage, screenManager);
        presenter = new GreetingPresenter(this,
                new GreetingInteractor(localUserDataRepository, databaseRepository));

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
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");

        label.setFontScale(2);
        layer.add(label).row();
        layer.add(usernameField).padTop(BUTTON_PADDING_TOP).height(64).row();
        layer.add(UIElementFactory.getButton(skin, GREETING_SCREEN.BUTTON_TEXT, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (usernameField.getText().length() > 0) {
                    presenter.onStart(usernameField.getText());
                } else {
                    // inform user about wrong username
                }
            }
        })).bottom().padTop(BUTTON_PADDING_TOP).padBottom(BUTTON_PADDING_BOTTOM);

        return layer;
    }

    @Override
    public void onStart() {
        screenManager.setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
