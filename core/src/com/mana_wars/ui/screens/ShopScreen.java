package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserShopAPI;
import com.mana_wars.model.interactor.ShopInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.ShopPresenter;
import com.mana_wars.presentation.view.ShopView;
import com.mana_wars.ui.UIElementsSize;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.MenuOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.skill_window.BaseSkillWindow;
import com.mana_wars.ui.widgets.skill_window.SkillCaseWindow;

public final class ShopScreen extends BaseScreen<MenuOverlayUI, ShopPresenter> implements ShopView {

    private final BaseSkillWindow skillCaseWindow;

    public ShopScreen(final UserShopAPI user,
                      final Skin skin,
                      final ScreenSetter screenSetter,
                      final FactoryStorage factoryStorage,
                      final DatabaseRepository databaseRepository,
                      final MenuOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);
        presenter = new ShopPresenter(this, new ShopInteractor(user, databaseRepository),
                Gdx.app::postRunnable);
        presenter.addObserver_manaAmount(overlayUI.getManaAmountObserver());
        presenter.addObserver_userLevel(overlayUI.getUserLevelObserver());

        skillCaseWindow = new SkillCaseWindow(UIStringConstants.SKILL_CASE_WINDOW.TITLE, skin,
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory());
    }

    @Override
    void rebuildStage() {
        super.rebuildStage();
        addActor(skillCaseWindow.build());
    }

    @Override
    public void show() {
        super.show();
        // refresh offers list
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

        layer.add(UIElementFactory.getButton(skin, "1 SKILL CASE (" + GameConstants.SKILL_CASE_PRICE + " MANA)", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.buySkillCase();
            }
        })).top().padTop(UIElementsSize.MENU_OVERLAY_UI.USER_LEVEL_FIELD_HEIGHT()).expandX().fillX().row();

        return layer;
    }

    @Override
    public void openSkillCaseWindow(Skill skill) {
        skillCaseWindow.open(skill);
    }
}
