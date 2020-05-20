package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ManaWars;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.UIStringConstants.*;

public class MainMenuScreen extends BaseScreen implements MainMenuView {

    private final Skin skin;

    private final SkillCaseWindow skillCaseWindow;
    private final NavigationBar navigationBar;

    private final MainMenuPresenter presenter;

    MainMenuScreen(FactoryStorage factoryStorage, ScreenManager screenManager,
                   LocalUserDataRepository localUserDataRepository, DatabaseRepository databaseRepository) {
        super(factoryStorage, screenManager);
        presenter = new MainMenuPresenter(this,
                new MainMenuInteractor(localUserDataRepository, databaseRepository));

        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
        skillCaseWindow = new SkillCaseWindow(SKILL_CASE_WINDOW.TITLE, skin, factoryStorage.getSkillIconFactory());
        navigationBar = screenManager.getNavigationBar();
    }

    @Override
    protected void rebuildStage() {
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

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        TextButton skillCaseButton = UIElementFactory.getButton(skin, MAIN_MENU_SCREEN.OPEN_SKILL_CASE_BUTTON_TEXT,
                new ChangeListener() {
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
    public void dispose() {
        super.dispose();
        skin.dispose();
        presenter.dispose();
    }
}
