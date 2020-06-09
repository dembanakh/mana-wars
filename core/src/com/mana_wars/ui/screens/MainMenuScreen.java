package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ManaWars;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.model.mana_bonus.ManaBonusImpl;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.MenuOverlayUI;
import com.mana_wars.ui.overlays.OverlayUI;
import com.mana_wars.ui.widgets.ManaBonusProgressBar;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.SkillCaseWindow;

import static com.mana_wars.ui.UIStringConstants.*;

public class MainMenuScreen extends BaseScreen implements MainMenuView {

    private final Skin skin;
    private final MenuOverlayUI overlayUI;

    private final SkillCaseWindow skillCaseWindow;
    private final ManaBonusProgressBar manaBonusProgressBar;

    private final MainMenuPresenter presenter;

    public MainMenuScreen(ScreenSetter screenSetter, FactoryStorage factoryStorage,
                          RepositoryStorage repositoryStorage, MenuOverlayUI overlayUI) {
        super(screenSetter);
        this.overlayUI = overlayUI;

        this.presenter = new MainMenuPresenter(this,
                Gdx.app::postRunnable,
                new MainMenuInteractor(
                        ManaWars.getInstance().getUser(),
                        repositoryStorage.getLocalUserDataRepository(),
                        repositoryStorage.getDatabaseRepository(),
                        new ManaBonusImpl(
                                GameConstants.MANA_BONUS_BIT_TIMEOUT,
                                GameConstants.MANA_BONUS_BIT_SIZE,
                                GameConstants.MANA_BONUS_NUM_BITS,
                                System::currentTimeMillis,
                                repositoryStorage.getLocalUserDataRepository())));
        presenter.addObserver_manaAmount(overlayUI.getManaAmountObserver());
        presenter.addObserver_userLevel(overlayUI.getUserLevelObserver());
        presenter.addObserver_userName(overlayUI.getUsernameObserver());

        this.skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
        this.skillCaseWindow = new SkillCaseWindow(SKILL_CASE_WINDOW.TITLE, skin,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory());
        this.manaBonusProgressBar = new ManaBonusProgressBar(presenter.getFullManaBonusTimeout(),
                this::claimBonus, skin);
    }

    @Override
    protected Skin getSkin() {
        return skin;
    }

    @Override
    protected OverlayUI getOverlayUI() {
        return overlayUI;
    }

    @Override
    protected void rebuildStage() {
        super.rebuildStage();
        addActor(skillCaseWindow.build(skin));
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
        // TODO: remove constants
        layer.add(manaBonusProgressBar.build(skin)).height(100).width(200);

        return layer;
    }

    private void claimBonus() {
        presenter.claimBonus();
    }

    @Override
    public void openSkillCaseWindow(int skillID, String skillName, Rarity skillRarity, String description) {
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
        presenter.onScreenShow();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        presenter.dispose();
    }

}
