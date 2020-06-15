package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BattleBaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.model.entity.battle.PvEBattle;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.presenters.BattlePresenter;
import com.mana_wars.presentation.view.BattleView;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.skills_list_2d.ApplicableSkillsList2D;
import com.mana_wars.ui.widgets.skills_list_2d.BlockableSkillsList;


import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIStringConstants.*;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;

public class BattleScreen extends BaseScreen<BattleBaseOverlayUI, BattlePresenter> implements BattleView {

    private final BlockableSkillsList<ActiveSkill> userActiveSkills;
    private final AtomicBoolean isBattle = new AtomicBoolean(false);

    public BattleScreen(final UserBattleAPI user,
                        final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                        final RepositoryStorage repositoryStorage, final BattleBaseOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING), overlayUI);

        presenter = new BattlePresenter(this,
                new BattleInteractor(user, repositoryStorage.getDatabaseRepository()),
                Gdx.app::postRunnable);
        presenter.addObserver_userManaAmount(overlayUI.getUserManaAmountObserver());

        userActiveSkills = new ApplicableSkillsList2D<ActiveSkill>(getSkin(), GameConstants.USER_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                (skillIndex) -> {
                    if (isBattle.get()) {
                        presenter.applyUserSkill(skillIndex);
                    }
                }) {
            @Override
            protected boolean shouldShowLevel(ActiveSkill item) {
                return false;
            }
        };
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        userActiveSkills.blockSkills(appliedSkillIndex);
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        super.reInit(arguments);
        presenter.initBattle(new PvEBattle(presenter.getPreparedUser(),
                getArgument(arguments, "EnemyFactory")));
        return this;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        userActiveSkills.update(delta);

        if (isBattle.get()) {
            presenter.updateBattle(delta);
        }
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
                        isBattle.set(false);
                        overlayUI.clear();
                        setScreen(ScreenInstance.MAIN_MENU, null);
                    }
                });

        layer.add(backButton).pad(200, 0, 100, 0).bottom().expand().row();
        layer.add(userActiveSkills.toActor()).bottom().expand()
                .height(ACTIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();
        return layer;
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills) {
        userActiveSkills.setItems(activeSkills);
    }

    @Override
    public void setUser(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills,
                        Consumer<Consumer<? super Integer>> subscribe) {
        try {
            subscribe.accept(overlayUI.setUser(name, initialHealth, passiveSkills));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEnemy(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills,
                         Consumer<Consumer<? super Integer>> subscribe) {
        try {
            subscribe.accept(overlayUI.addEnemy(name, initialHealth, passiveSkills));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActiveEnemy(int index) {
        overlayUI.setActiveEnemy(index);
    }

    @Override
    public void startBattle() {
        isBattle.set(true);
    }

    @Override
    public void finishBattle() {
        isBattle.set(false);
        overlayUI.clear();
        setScreen(ScreenInstance.BATTLE_SUMMARY, null);
    }

}
