package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ManaWars;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.factory.UIElementFactory;

public class MainMenuScreen extends BaseScreen implements MainMenuView {

    private final Stage stage;
    private final Skin skin;

    private final SkillCaseWindow skillCaseWindow;
    private final NavigationBar navigationBar;

    private final MainMenuPresenter presenter;

    MainMenuScreen() {
        //TODO think about rewrite
        presenter = new MainMenuPresenter(this,
                new MainMenuInteractor(ManaWars.getInstance().getLocalUserDataRepository()));

        stage = new Stage();
        skin = ManaWars.getInstance().getScreenManager().getSkinFactory().getAsset("freezing");
        skillCaseWindow = new SkillCaseWindow("NEW SKILL", skin);
        navigationBar = ManaWars.getInstance().getScreenManager().getNavigationBar();
    }

    private void rebuildStage() {
        // layers
        Table layerBackground = buildBackgroundLayer(skin);
        Table layerForeground = buildForegroundLayer(skin);
        Table layerNavigationBar = navigationBar.rebuild(skin);
        Table layerSkillCaseWindow = skillCaseWindow.rebuild(skin);

        // fill stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(layerBackground);
        stack.add(layerForeground);
        stage.addActor(layerSkillCaseWindow);
        stage.addActor(layerNavigationBar);
    }

    private Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    private Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        TextButton skillCaseButton = UIElementFactory.getButton(skin, "OPEN SKILL CASE", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.onOpenSkillCase();
            }
        });
        layer.add(skillCaseButton);

        return layer;
    }

    @Override
    public void openSkillCaseWindow(int skillID, String skillName, String description) {
        skillCaseWindow.open(skillID, skillName, description);
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
