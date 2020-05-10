package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mana_wars.ManaWars;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.AssetsFactory;
import com.mana_wars.ui.LocalizedStringsRepository;
import com.mana_wars.ui.UIElementsFactory;

public class MainMenuScreen extends BaseScreen implements MainMenuView {

    private final Stage stage;
    private final Skin skin;

    private SkillCaseWindow skillCaseWindow;

    private final MainMenuPresenter presenter;

    MainMenuScreen() {
        //TODO think about rewrite
        presenter = new MainMenuPresenter(this,
                new MainMenuInteractor(ManaWars.getInstance().getLocalUserDataRepository()));

        stage = new Stage();
        skin = AssetsFactory.getSkin("freezing");
        skillCaseWindow = new SkillCaseWindow("NEW SKILL", skin);
    }

    private void rebuildStage() {
        // layers
        Table layerBackground = buildBackgroundLayer(skin);
        Table layerForeground = buildForegroundLayer(skin);
        Table navigationBar = buildNavigationBar(skin);
        Table layerSkillCaseWindow = skillCaseWindow.rebuild(skin);

        // fill stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(layerBackground);
        stack.add(layerForeground);
        stack.add(layerSkillCaseWindow);
        stage.addActor(navigationBar);
    }

    private Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    private Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        TextButton skillCaseButton = UIElementsFactory.getButton(skin, "OPEN SKILL CASE", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("OPEN SKILL CASE");
                skillCaseWindow.onOpenSkillCase();
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
        layer.add(UIElementsFactory.getButton(skin, "SKILLS", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("SKILLS");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER1
        layer.add(UIElementsFactory.getButton(skin, "PLACEHOLDER1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER1");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER2
        layer.add(UIElementsFactory.getButton(skin, "PLACEHOLDER2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER2");
            }
        })).width(buttonWidth).height(buttonHeight);
        // PLACEHOLDER3
        layer.add(UIElementsFactory.getButton(skin, "PLACEHOLDER3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("PLACEHOLDER3");
            }
        })).width(buttonWidth).height(buttonHeight);

        return layer;
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
