package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ManaWars;
import com.mana_wars.ui.AssetsFactory;

class GreetingScreen extends BaseScreen {

    private final Stage stage;
    private final Skin skin;

    GreetingScreen() {
        stage = new Stage();
        skin = AssetsFactory.getSkin("freezing");
    }

    private void rebuildStage() {
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

    private Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    private Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        Label label = new Label("HELLO, USER", skin);
        label.setFontScale(2);
        layer.add(label).row();
        layer.add(getButton(skin, "START", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("START");
                onStart();
            }
        })).bottom().padBottom(100).padTop(200);

        return layer;
    }

    private TextButton getButton(Skin skin, String label, ChangeListener eventListener) {
        TextButton button = new TextButton(label, skin);
        button.addListener(eventListener);
        return button;
    }

    private void onStart() {
        ManaWars.getInstance().getScreenManager().setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
    }

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
