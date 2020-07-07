package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
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
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeHeightConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.transform.TransformFactory;
import com.mana_wars.ui.transform.base.Transform;
import com.mana_wars.ui.transform.base.TransformBuilder;
import com.mana_wars.ui.widgets.skills_list_2d.BlockableSkillsList;
import com.mana_wars.ui.widgets.value_field.EnemyValueField;
import com.mana_wars.ui.widgets.value_field.ValueFieldFactory;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.functions.Consumer;

import static com.mana_wars.model.GameConstants.CHOSEN_BATTLE_BUILDER;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public class BattleScreen extends BaseScreen<BaseOverlayUI, BattlePresenter> implements BattleView {

    private final ValueField<BattleParticipantData, Integer> userField;
    private final EnemyValueField enemyField;

    private final BlockableSkillsList<ActiveSkill> userActiveSkills;

    private final ValueField<Integer, Integer> aliveEnemiesField;
    private final ValueField<Void, Integer> userManaAmountField;

    private final AtomicBoolean isBattle = new AtomicBoolean(false);
    private final AssetFactory<String, Texture> imageFactory;

    public BattleScreen(final UserBattleAPI user,
                        final Skin skin,
                        final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                        final DatabaseRepository databaseRepository, final BaseOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);

        presenter = new BattlePresenter(this,
                new BattleInteractor(user, databaseRepository),
                Gdx.app::postRunnable);

        Transform transform;

        transform = new TransformBuilder()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new RelativeHeightConstraint(75))
                .build();
        userField = ValueFieldFactory.battleParticipantValueField(skin,
                TransformFactory.manualTransform(transform),
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory(),
                -200, 1);

        transform = new TransformBuilder()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new RelativeHeightConstraint(75))
                .build();
        enemyField = new EnemyValueField(skin,
                TransformFactory.manualTransform(transform),
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory());//*/

        /*userField = ValueFieldFactory.battleParticipantValueField(skin,
                TransformFactory.autoTransform(),
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory(),
                -200, 1);

        enemyField = new EnemyValueField(skin,
                TransformFactory.autoTransform(),
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory());*/

        userActiveSkills = UIElementFactory.applicableSkillsList(skin,
                GameConstants.MAX_CHOSEN_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory(), this::onSkillClick);

        aliveEnemiesField = ValueFieldFactory.aliveEnemiesValueField(skin, UI_SKIN.BACKGROUND_COLOR.BROWN,
                TransformFactory.autoTransform());

        userManaAmountField = ValueFieldFactory.textValueField(skin, TransformFactory.autoTransform());
        presenter.addObserver_userManaAmount(userManaAmountField);

        imageFactory = factoryStorage.getImageFactory();
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        userActiveSkills.blockSkills(appliedSkillIndex);
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        super.reInit(arguments);
        enemyField.clear();
        presenter.initBattle(getArgument(arguments, CHOSEN_BATTLE_BUILDER));
        return this;
    }

    @Override
    public void cleanEnemies(int enemiesCount) {
        enemyField.clear();
        aliveEnemiesField.setInitialData(enemiesCount);
    }


    //TODO delete
    Label temp;
    @Override
    public void setRound(int round) {
        temp.setText(String.format("Round: %d", round));
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

        //layer.left().top();

        //layer.add(userField.build()).uniformX().expandX();
        //layer.add(enemyField.build()).uniformX().expandX();

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
        changeEnemySection.add(aliveEnemiesField.build()).padRight(20);
        battleUtility.add(changeEnemySection).right().colspan(3).expandX().row();

        Table battleInfoSection = new Table();
        battleInfoSection.add(userManaAmountField.build()).center().uniformX().expandX();
        battleInfoSection.add(temp = new Label("", skin)).center().uniformX().expandX();
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

        layer.add(userActiveSkills.build()).padTop(10).height(ACTIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();

        stage.addActor(userField.build());
        stage.addActor(enemyField.build());

        return layer;
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills) {
        userActiveSkills.setItems(activeSkills);
    }

    @Override
    public void setUser(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills,
                        Consumer<Consumer<? super Integer>> subscribe) {
        userField.setInitialData(new BattleParticipantData(name, initialHealth, passiveSkills));
        try {
            subscribe.accept(userField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEnemy(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills,
                         Consumer<Consumer<? super Integer>> subscribe) {
        try {
            subscribe.accept(enemyField.addEnemy(name, initialHealth, passiveSkills));
            subscribe.accept(aliveEnemiesField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActiveEnemy(int index) {
        enemyField.setActiveEnemy(index);
    }

    @Override
    public void startBattle(int enemiesNumber) {
        aliveEnemiesField.setInitialData(enemiesNumber);
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

    public static class BattleParticipantData {
        public final String name;
        public final int initialHealth;
        public final Iterable<PassiveSkill> passiveSkills;

        public BattleParticipantData(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills) {
            this.name = name;
            this.initialHealth = initialHealth;
            this.passiveSkills = passiveSkills;
        }
    }

}
