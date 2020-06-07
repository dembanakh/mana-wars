package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BattleOverlayUI;
import com.mana_wars.ui.overlays.OverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.battle.PvEBattle;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.presenters.BattlePresenter;
import com.mana_wars.presentation.view.BattleView;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.skills_list_2d.ApplicableSkillsList2D;
import com.mana_wars.ui.widgets.skills_list_2d.List2D;
import com.mana_wars.ui.widgets.skills_list_2d.StaticSkillsList2D;


import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.PASSIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIStringConstants.*;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;

public class BattleScreen extends BaseScreen implements BattleView {

    private final Skin skin;
    private final BattleOverlayUI overlayUI;

    private final BattlePresenter presenter;

    private final List2D<ActiveSkill> userActiveSkills;
    private final List2D<PassiveSkill> userPassiveSkills;

    private final AtomicBoolean isBattle = new AtomicBoolean(false);

    public BattleScreen(ScreenSetter screenSetter, FactoryStorage factoryStorage,
                        RepositoryStorage repositoryStorage, BattleOverlayUI overlayUI) {
        super(screenSetter);
        this.overlayUI = overlayUI;

        presenter = new BattlePresenter(this,
                new BattleInteractor(repositoryStorage.getLocalUserDataRepository(),
                        repositoryStorage.getDatabaseRepository()),
                Gdx.app::postRunnable);

        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);

        userActiveSkills = new ApplicableSkillsList2D(skin, GameConstants.USER_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory())
                .setOnSkillClick((skill) -> {
                    if (isBattle.get()) {
                        this.blockSkill(0);
                        presenter.applyUserSkill(skill);
                    }
                });
        userPassiveSkills = new StaticSkillsList2D<>(skin, GameConstants.USER_PASSIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory());
    }

    private void blockSkill(int index) {
        userActiveSkills.setSelectedIndex(index);
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        super.reInit(arguments);
        presenter.initBattle(new PvEBattle(new User(), getArgument("EnemyFactory")));
        presenter.addObserver_userHealth(overlayUI.getUserHealthObserver());
        presenter.addObserver_enemyHealth(overlayUI.getEnemyHealthObserver());
        return this;
    }

    @Override
    public void startBattle() {
        // create sufficient number of Health bars in BattleOverlayUI
        isBattle.set(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (isBattle.get()) {
            presenter.updateBattle(delta);
        }
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
    public void show() {
        super.show();
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

        TextButton backButton = UIElementFactory.getButton(skin, "BACK",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        setScreen(ScreenInstance.MAIN_MENU, null);
                    }
                });

        userActiveSkills.getSelection().setMultiple(true);
        userActiveSkills.getSelection().setRequired(false);
        userPassiveSkills.getSelection().setMultiple(true);
        userPassiveSkills.getSelection().setRequired(false);

        layer.add(backButton).pad(100).row();
        layer.add(userActiveSkills).bottom().expandX()
                .height(ACTIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();
        layer.add(userPassiveSkills).bottom().expandX()
                .height(PASSIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();
        return layer;
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        presenter.dispose();
    }

    @Override
    public void setSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills) {
        userActiveSkills.setItems(activeSkills);
        userPassiveSkills.setItems(passiveSkills);
    }

    @Override
    public void finishBattle() {
        isBattle.set(false);
    }

}
