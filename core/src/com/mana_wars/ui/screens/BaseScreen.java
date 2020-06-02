package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.overlays.OverlayUI;

import java.util.Map;

public abstract class BaseScreen implements Screen {

    private final Stage stage;

    private final ScreenManager screenManager;

    private Map<String, Object> arguments;

    BaseScreen(ScreenManager screenManager) {
        this.stage = new Stage() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                unfocusAll();
                Gdx.input.setOnscreenKeyboardVisible(false);
                return super.touchDown(screenX, screenY, pointer, button);
            }
        };
        this.screenManager = screenManager;
    }

    public BaseScreen setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgument(String key) {
        if (!arguments.containsKey(key)) return null;
        return (T)arguments.get(key);
    }

    public abstract void init();

    protected abstract Skin getSkin();
    protected abstract OverlayUI getOverlayUI();

    protected void rebuildStage() {
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(buildBackgroundLayer(getSkin()));
        stack.add(buildForegroundLayer(getSkin()));
    }

    protected void addActor(Actor actor) {
        stage.addActor(actor);
    }

    protected abstract Table buildBackgroundLayer(Skin skin);
    protected abstract Table buildForegroundLayer(Skin skin);

    protected void setScreen(ScreenManager.ScreenInstance instance) {
        screenManager.setScreen(instance);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
        getOverlayUI().overlay(stage, getSkin());
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
        getOverlayUI().overlay(stage, getSkin());
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
