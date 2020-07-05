package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.battle.BattleSummaryData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.BattlePresenter;
import com.mana_wars.presentation.view.BattleView;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BattleOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.value_field.NumberOfAliveEnemiesValueField;
import com.mana_wars.ui.widgets.skills_list_2d.BlockableSkillsList;
import com.mana_wars.ui.widgets.value_field.TextValueField;
import com.mana_wars.ui.widgets.value_field.ValueField;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.functions.Consumer;

import static com.mana_wars.model.GameConstants.CHOSEN_BATTLE_BUILDER;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public class BattleScreen extends BaseScreen<BattleOverlayUI, BattlePresenter> implements BattleView {

    private final BlockableSkillsList<ActiveSkill> userActiveSkills;

    private final NumberOfAliveEnemiesValueField aliveEnemiesField;
    private final ValueField<Integer> userManaAmount;

    private final AtomicBoolean isBattle = new AtomicBoolean(false);
    private final AssetFactory<String, Texture> imageFactory;

    public BattleScreen(final UserBattleAPI user,
                        final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                        final DatabaseRepository databaseRepository, final BattleOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UI_SKIN.MANA_WARS), overlayUI);

        presenter = new BattlePresenter(this,
                new BattleInteractor(user, databaseRepository),
                Gdx.app::postRunnable);

        userActiveSkills = UIElementFactory.applicableSkillsList(getSkin(),
                GameConstants.MAX_CHOSEN_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory(), this::onSkillClick);

        aliveEnemiesField = new NumberOfAliveEnemiesValueField();
        aliveEnemiesField.setBackgroundColor(UI_SKIN.BACKGROUND_COLOR.BROWN);
        userManaAmount = new TextValueField<>();
        userManaAmount.init();
        presenter.addObserver_userManaAmount(userManaAmount);

        imageFactory = factoryStorage.getImageFactory();
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        userActiveSkills.blockSkills(appliedSkillIndex);
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        super.reInit(arguments);
        overlayUI.clear();
        aliveEnemiesField.init();
        presenter.initBattle(getArgument(arguments, CHOSEN_BATTLE_BUILDER));
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

        layer.add(new Image(imageFactory.getAsset("bg1")));

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        layer.center().bottom();

        int screenWidth = SCREEN_WIDTH();
        Table battleUtility = new Table();

        Table changeEnemySection = new Table();
        changeEnemySection.center();
        changeEnemySection.add(UIElementFactory.getButton(skin, "CHANGE ENEMY", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeActiveEnemy();
            }
        })).padRight(20);
        changeEnemySection.add(aliveEnemiesField.build(skin)).padRight(20);
        battleUtility.add(changeEnemySection).right().colspan(3).expandX().row();

        Table battleInfoSection = new Table();
        battleInfoSection.add(userManaAmount.build(skin)).center().uniformX().expandX();
        battleInfoSection.add(new Label("Round: %d", skin)).center().uniformX().expandX();
        battleInfoSection.add(UIElementFactory.getButton(skin, "LEAVE",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        isBattle.set(false);
                        setScreen(ScreenInstance.MAIN_MENU, null);
                    }
                })).right().uniformX().expandX();
        battleUtility.add(battleInfoSection).center().growX();

        layer.add(battleUtility).width(screenWidth).row();

        layer.add(userActiveSkills.toActor()).padTop(10)
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
        aliveEnemiesField.addEnemy();
        try {
            subscribe.accept(overlayUI.addEnemy(name, initialHealth, passiveSkills));
            subscribe.accept(aliveEnemiesField);
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
    public void finishBattle(BattleSummaryData summaryData) {
        isBattle.set(false);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("BattleSummaryData", summaryData);
        setScreen(ScreenInstance.BATTLE_SUMMARY, arguments);
    }

    private void changeActiveEnemy() {
        presenter.changeActiveEnemy();
    }

    private void onSkillClick(ActiveSkill skill, int index) {
        if (isBattle.get()) {
            presenter.applyUserSkill(index);
        }
    }

}
