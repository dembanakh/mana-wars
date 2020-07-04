package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserMenuAPI;
import com.mana_wars.model.interactor.MainMenuInteractor;
import com.mana_wars.model.mana_bonus.ManaBonusImpl;
import com.mana_wars.presentation.presenters.MainMenuPresenter;
import com.mana_wars.presentation.view.MainMenuView;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.MenuOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.widgets.ManaBonusProgressBar;
import com.mana_wars.ui.widgets.skill_window.BaseSkillWindow;
import com.mana_wars.ui.widgets.skill_window.SkillCaseWindow;

import static com.mana_wars.ui.UIStringConstants.MAIN_MENU_SCREEN;
import static com.mana_wars.ui.UIStringConstants.SKILL_CASE_WINDOW;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public class MainMenuScreen extends BaseScreen<MenuOverlayUI, MainMenuPresenter> implements MainMenuView {

    private final BaseSkillWindow skillCaseWindow;
    private final ManaBonusProgressBar manaBonusProgressBar;

    private final AssetFactory<String, Texture> imageFactory;

    public MainMenuScreen(final UserMenuAPI user,
                          final ScreenSetter screenSetter,
                          final FactoryStorage factoryStorage,
                          final RepositoryStorage repositoryStorage,
                          final MenuOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UI_SKIN.MANA_WARS), overlayUI);

        presenter = new MainMenuPresenter(this,
                new MainMenuInteractor(
                        user, repositoryStorage.getDatabaseRepository(),
                        new ManaBonusImpl(
                                GameConstants.MANA_BONUS_BIT_TIMEOUT,
                                GameConstants.MANA_BONUS_BIT_SIZE,
                                GameConstants.MANA_BONUS_NUM_BITS,
                                System::currentTimeMillis,
                                repositoryStorage.getManaBonusRepository())),
                Gdx.app::postRunnable);
        presenter.addObserver_manaAmount(overlayUI.getManaAmountObserver());
        presenter.addObserver_userLevel(overlayUI.getUserLevelObserver());

        skillCaseWindow = new SkillCaseWindow(SKILL_CASE_WINDOW.TITLE, getSkin(),
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory());
        manaBonusProgressBar = new ManaBonusProgressBar(presenter.getFullManaBonusTimeout(),
                this::claimBonus, getSkin());
        imageFactory = factoryStorage.getImageFactory();
    }

    @Override
    protected void rebuildStage() {
        super.rebuildStage();
        addActor(skillCaseWindow.build(getSkin()));
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        layer.add(new Image(imageFactory.getAsset("bg1")));

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
    public void openSkillCaseWindow(Skill skill) {
        skillCaseWindow.open(skill);
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
    public void setUsername(String username) {
        overlayUI.setUsername(username);
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

}
