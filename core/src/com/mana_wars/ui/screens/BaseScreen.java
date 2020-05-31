package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.screens.util.OverlayUIFactory;

import java.util.Map;

abstract class BaseScreen implements Screen {

    protected final Stage stage;

    protected final ScreenManager screenManager;
    protected final FactoryStorage factoryStorage;
    protected final RepositoryStorage repositoryStorage;
    protected final OverlayUI overlayUI;

    protected Map<String, Object> arguments;

    BaseScreen(ScreenManager screenManager, FactoryStorage factoryStorage,
              RepositoryStorage repositoryStorage, OverlayUI overlayUI) {
        this.stage = new Stage() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                unfocusAll();
                Gdx.input.setOnscreenKeyboardVisible(false);
                return super.touchDown(screenX, screenY, pointer, button);
            }
        };
        this.factoryStorage = factoryStorage;
        this.screenManager = screenManager;
        this.repositoryStorage = repositoryStorage;
        this.overlayUI = overlayUI;
    }

    BaseScreen setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
        return this;
    }

    protected abstract Skin getSkin();

    protected abstract void rebuildStage();

    protected abstract Table buildBackgroundLayer(Skin skin);
    protected abstract Table buildForegroundLayer(Skin skin);

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
        overlayUI.overlay(stage, getSkin());
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
        overlayUI.overlay(stage, getSkin());
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
