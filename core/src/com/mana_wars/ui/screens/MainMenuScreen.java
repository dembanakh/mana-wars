package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.model.mana_bonus.ManaBonusImpl;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.callback.MenuOverlayUICallbacks;
import com.mana_wars.ui.overlays.OverlayUI;
import com.mana_wars.ui.widgets.ManaBonusProgressBar;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.factory.UIElementFactory;

import java.util.List;

import static com.mana_wars.ui.UIStringConstants.*;

public class MainMenuScreen extends BaseScreen implements MainMenuView {

    private final Skin skin;

    private final SkillCaseWindow skillCaseWindow;
    private final ManaBonusProgressBar manaBonusProgressBar;

    private final MainMenuPresenter presenter;

    public MainMenuScreen(ScreenManager screenManager, FactoryStorage factoryStorage,
                   RepositoryStorage repositoryStorage, OverlayUI overlayUI,
                   MenuOverlayUICallbacks callbacks) {
        super(screenManager, factoryStorage, repositoryStorage, overlayUI);
        presenter = new MainMenuPresenter(this,
                Gdx.app::postRunnable,
                new MainMenuInteractor(repositoryStorage.getLocalUserDataRepository(),
                        repositoryStorage.getDatabaseRepository(),
                        new ManaBonusImpl(1, 20, 4,
                                            System::currentTimeMillis,
                                            repositoryStorage.getLocalUserDataRepository())));
        presenter.initCallbacks(callbacks.getManaAmountCallback(),
                                callbacks.getUserLevelCallback());

        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
        skillCaseWindow = new SkillCaseWindow(SKILL_CASE_WINDOW.TITLE, skin,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory());
        manaBonusProgressBar = new ManaBonusProgressBar(presenter.getFullManaBonusTimeout(), skin,
                presenter::claimBonus);
    }

    @Override
    protected Skin getSkin() {
        return skin;
    }

    @Override
    protected void rebuildStage() {
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(buildBackgroundLayer(skin));
        stack.add(buildForegroundLayer(skin));
        stage.addActor(skillCaseWindow.rebuild(skin));
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
        layer.add(skillCaseButton).row();
        layer.add(manaBonusProgressBar.rebuild(skin)).height(100).width(200);

        return layer;
    }

    @Override
    public void openSkillCaseWindow(int skillID, String skillName, Rarity skillRarity, List<String> description) {
        skillCaseWindow.open(skillID, skillName, skillRarity, description);
    }

    @Override
    public void setTimeSinceLastManaBonusClaimed(long time) {
        manaBonusProgressBar.setTimeSinceLastBonusClaim(time);
    }

    @Override
    public void onManaBonusReady() {
        manaBonusProgressBar.onBonusReady();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (manaBonusProgressBar.shouldSynchronizeNow())
            presenter.synchronizeManaBonusTime();
        else manaBonusProgressBar.update(delta);
    }

    @Override
    public void show() {
        super.show();
        presenter.init();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        presenter.dispose();
    }

}
