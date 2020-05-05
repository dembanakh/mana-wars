package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mana_wars.ManaWars;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.AssetsFactory;
import com.mana_wars.ui.LocalizedStringsRepository;

public class MainMenuScreen extends com.mana_wars.ui.screens.BaseScreen implements MainMenuView {

    private Stage stage;
    private MainMenuPresenter presenter;

    MainMenuScreen() {
        //TODO think about rewrite
        presenter = new MainMenuPresenter(this, new MainMenuInteractor(ManaWars.getInstance().getLocalUserDataRepository()));

        stage = new Stage();

        stage.addActor(initTabsBar(
                ManaWars.getInstance().getLocalizedStringsRepository().get(LocalizedStringsRepository.SKILL_LABEL),
                "PLACEHOLDER1", "PLACEHOLDER2", "PLACEHOLDER3"));

        TextButton skillCaseButton = initTab("GET SKILL CASE", new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("TOUCH " + pointer + " " + button);
                // ManaWars.getInstance().getScreenManager().switchScreenTo();
            }
        });
        skillCaseButton.setPosition(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        skillCaseButton.setSize(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getWidth() * 0.2f);
        stage.addActor(skillCaseButton);
    }

    private Table initTabsBar(String... labels) {
        Table table = new Table(AssetsFactory.getSkin("freezing"));
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        table.setSize(screenWidth, screenHeight * 0.1f);
        table.setPosition(0, 0);
        table.setBackground("white");
        table.setDebug(false);

        int tabsNumber = labels.length;

        for (String label : labels) {
            table.add(initTab(label, new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("TOUCH " + pointer + " " + event.getButton());
                    // ManaWars.getInstance().getScreenManager().switchScreenTo();
                }
            }))
                    .width((float)screenWidth / tabsNumber)
                    .height(table.getHeight());
        }

        return table;
    }

    private TextButton initTab(String label, EventListener eventListener) {
        TextButton skillsTabButton = new TextButton(label, AssetsFactory.getSkin("freezing"));
        skillsTabButton.addListener(eventListener);
        return skillsTabButton;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
    }
}
