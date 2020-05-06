package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ManaWars;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.AssetsFactory;
import com.mana_wars.ui.LocalizedStringsRepository;

public class MainMenuScreen extends BaseScreen implements MainMenuView {

    private Stage stage;
    private Skin skin;

    private MainMenuPresenter presenter;

    MainMenuScreen() {
        //TODO think about rewrite
        presenter = new MainMenuPresenter(this,
                new MainMenuInteractor(ManaWars.getInstance().getLocalUserDataRepository()));
    }

    private void rebuildStage() {
        skin = AssetsFactory.getSkin("freezing");
        // layers
        Table layerBackground = buildBackgroundLayer(skin);
        Table layerForeground = buildForegroundLayer(skin);
        Table navigationBar = buildNavigationBar(skin);

        // fill stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(layerBackground);
        stack.add(layerForeground);
        stage.addActor(navigationBar);
    }

    private Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    private Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        TextButton skillCaseButton = getButton(skin, "OPEN SKILL CASE", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("OPEN SKILL CASE");
            }
        });
        layer.add(skillCaseButton);

        return layer;
    }

    private Table buildNavigationBar(Skin skin) {
        Table layer = new Table(skin);
        layer.bottom().setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.1f);
        layer.setBackground("white");

        int tabsNumber = 4;
        float buttonWidth = (float)Gdx.graphics.getWidth() / tabsNumber;
        float buttonHeight = layer.getHeight();

        // SKILLS
        layer.add(getButton(skin, "SKILLS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("SKILLS");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER1
        layer.add(getButton(skin, "PLACEHOLDER1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER1");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER2
        layer.add(getButton(skin, "PLACEHOLDER2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER2");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER3
        layer.add(getButton(skin, "PLACEHOLDER3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER3");
            }
        })).width(buttonWidth).height(buttonHeight);

        return layer;
    }

    private TextButton getButton(Skin skin, String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, skin);
        button.addListener(eventListener);
        return button;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50.0f/255, 115.0f/255, 220.0f/255, 0.3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}