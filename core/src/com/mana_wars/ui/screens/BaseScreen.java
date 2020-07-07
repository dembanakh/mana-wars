package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.presentation.presenters.BasePresenter;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;

import java.util.Map;
import java.util.NoSuchElementException;

public abstract class BaseScreen<U extends BaseOverlayUI, T extends BasePresenter> implements Screen, ScreenSetter {

    T presenter;
    protected final U overlayUI;

    private final Skin skin;
    final Stage stage;
    private final ScreenSetter screenSetter;

    BaseScreen(ScreenSetter screenSetter, Skin skin, U overlayUI) {
        this.skin = skin;
        this.stage = new Stage() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                unfocusAll();
                Gdx.input.setOnscreenKeyboardVisible(false);
                return super.touchDown(screenX, screenY, pointer, button);
            }
        };
        this.overlayUI = overlayUI;
        this.screenSetter = screenSetter;
    }

    public BaseScreen reInit(Map<String, Object> arguments) {
        return this;
    }

    @SuppressWarnings("unchecked")
    static <T> T getArgument(Map<String, Object> arguments, String key) {
        if (!arguments.containsKey(key))
            throw new NoSuchElementException("There is no argument for key " + key);
        return (T) arguments.get(key);
    }

    Skin getSkin() {
        return skin;
    }

    void rebuildStage() {
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(buildBackgroundLayer(getSkin()));
        stack.add(buildForegroundLayer(getSkin()));
    }

    void addActor(Actor actor) {
        stage.addActor(actor);
    }

    protected abstract Table buildBackgroundLayer(Skin skin);

    protected abstract Table buildForegroundLayer(Skin skin);

    @Override
    public void setScreen(ScreenInstance instance, Map<String, Object> arguments) {
        screenSetter.setScreen(instance, arguments);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
        overlayUI.overlay(stage, getSkin());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50.0f / 255, 115.0f / 255, 220.0f / 255, 0.3f);
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
        skin.dispose();
        presenter.dispose();
    }

}
