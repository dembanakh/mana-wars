package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

abstract class BaseScreen implements Screen {

    final Stage stage;
    final FactoryStorage factoryStorage;
    final ScreenManager screenManager;

    BaseScreen(FactoryStorage factoryStorage, ScreenManager screenManager) {
        this.stage = new Stage();
        this.factoryStorage = factoryStorage;
        this.screenManager = screenManager;
    }

    protected abstract void rebuildStage();

    protected abstract Table buildBackgroundLayer(Skin skin);
    protected abstract Table buildForegroundLayer(Skin skin);

    @Override
    public void show() {
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
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
