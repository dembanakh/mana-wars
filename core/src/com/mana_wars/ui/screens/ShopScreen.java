package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.interactor.ShopInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.ShopRepository;
import com.mana_wars.presentation.presenters.ShopPresenter;
import com.mana_wars.presentation.view.ShopView;
import com.mana_wars.model.entity.skills.ShopSkill;
import com.mana_wars.ui.UIElementsSize;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.MenuOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.base.List2D;
import com.mana_wars.ui.widgets.skill_window.BaseSkillWindow;
import com.mana_wars.ui.widgets.skill_window.SkillCaseWindow;

import static com.mana_wars.ui.UIStringConstants.SHOP_SCREEN.*;

public final class ShopScreen extends BaseScreen<MenuOverlayUI, ShopPresenter> implements ShopView {

    private final BaseSkillWindow skillCaseWindow;
    private final TextButton skillCaseButton;

    private final List2D<ShopSkill> purchasableSkills;

    private final LocalizedStringFactory localizedStringFactory;

    public ShopScreen(final UserShopAPI user,
                      final Skin skin,
                      final ScreenSetter screenSetter,
                      final FactoryStorage factoryStorage,
                      final DatabaseRepository databaseRepository,
                      final ShopRepository shopRepository,
                      final MenuOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);
        presenter = new ShopPresenter(this, new ShopInteractor(user, databaseRepository, shopRepository),
                Gdx.app::postRunnable);
        presenter.addObserver_manaAmount(overlayUI.getManaAmountObserver());
        presenter.addObserver_userLevel(overlayUI.getUserLevelObserver());
        presenter.addObserver_userExperience(overlayUI.getUserExperienceObserver());
        presenter.addObserver_userNextLevelRequiredExperienceObserver(overlayUI.getUserNextLevelRequiredExperienceObserver());

        this.localizedStringFactory = factoryStorage.getLocalizedStringFactory();
        this.skillCaseWindow = new SkillCaseWindow(UIStringConstants.SKILL_CASE_WINDOW.TITLE, skin,
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory());
        this.skillCaseButton = UIElementFactory.getButton(skin,
                localizedStringFactory.format(UIStringConstants.MAIN_MENU_SCREEN.OPEN_SKILL_CASE_BUTTON_KEY, 0),
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        presenter.onOpenSkillCase();
                    }
                });
        this.purchasableSkills = UIElementFactory.purchasableSkillsList(skin, 3, factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(), this::purchaseSkill);
    }

    @Override
    void rebuildStage() {
        super.rebuildStage();
        addActor(skillCaseWindow.build());
        presenter.refreshSkillCasesNumber();
        presenter.refreshPurchasableSkills();
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
        layer.setDebug(true);

        layer.add(purchasableSkills).top().padTop(50).growX().row();
        layer.add(skillCaseButton).top().padTop(50).row();
        TextButton button = UIElementFactory.getButton(skin,
                localizedStringFactory.format(ONE_SKILL_CASE_KEY, GameConstants.SKILL_CASE_PRICE), new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        presenter.buySkillCase();
                    }
                });
        presenter.addObserver_manaAmount((mana) -> button.setDisabled(mana < GameConstants.SKILL_CASE_PRICE));
        layer.add(button).top().padTop(UIElementsSize.MENU_OVERLAY_UI.USER_LEVEL_FIELD_HEIGHT())
                .expandX().fillX().row();

        return layer;
    }

    @Override
    public void openSkillCaseWindow(Skill skill) {
        skillCaseWindow.open(skill);
    }

    @Override
    public void setSkillCasesNumber(int number) {
        skillCaseButton.setText(localizedStringFactory.format(UIStringConstants.MAIN_MENU_SCREEN.OPEN_SKILL_CASE_BUTTON_KEY, number));
    }

    @Override
    public void setPurchasableSkills(Iterable<? extends ShopSkill> skills) {
        purchasableSkills.setItems(skills);
    }

    private void purchaseSkill(ShopSkill skill, int index) {
        presenter.purchaseSkill(skill);
    }
}
