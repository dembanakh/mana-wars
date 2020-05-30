package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.interactor.TestBattleInteractor;
import com.mana_wars.presentation.presenters.TestBattlePresenter;
import com.mana_wars.presentation.view.TestBattleView;
import com.mana_wars.ui.screens.util.OverlayUIFactory;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.UIStringConstants.*;

class TestBattleScreen extends BaseScreen implements TestBattleView {

    private Label testLabel;

    private TestBattlePresenter presenter;

    @Override
    void init(ScreenManager screenManager, FactoryStorage factoryStorage,
              RepositoryStorage repositoryStorage, OverlayUIFactory overlayUIFactory) {
        super.init(screenManager, factoryStorage, repositoryStorage, overlayUIFactory);
        presenter = new TestBattlePresenter(this,
                new TestBattleInteractor(repositoryStorage.getLocalUserDataRepository(),
                        repositoryStorage.getDatabaseRepository()));

        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
        testLabel = new Label("TEXT", skin);
        overlayUI = overlayUIFactory.getEmptyOverlayUI();
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
        layer.setFillParent(true);

        TextButton testButton = UIElementFactory.getButton(skin, "PRESS",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        presenter.onButtonPress();
                    }
                });
        TextButton backButton = UIElementFactory.getButton(skin, "BACK",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        screenManager.setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
                    }
                });

        layer.add(testButton).pad(100).row();
        layer.add(testLabel).pad(100).row();
        layer.add(backButton).pad(100).row();

        return layer;
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        presenter.dispose();
    }

    @Override
    public void setLabelText(String text) {
        testLabel.setText(text);
    }
}
