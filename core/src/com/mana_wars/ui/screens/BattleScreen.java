package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.BattlePresenter;
import com.mana_wars.presentation.view.BattleView;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.transform.TransformFactory;
import com.mana_wars.ui.widgets.skills_list_2d.BlockableSkillsList;
import com.mana_wars.ui.widgets.value_field.EnemyValueField;
import com.mana_wars.ui.widgets.value_field.ValueFieldFactory;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.mana_wars.model.GameConstants.CHOSEN_BATTLE_BUILDER;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SCREEN.CHANGE_ENEMY_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SCREEN.LEAVE_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SCREEN.ROUND_KEY;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public final class BattleScreen extends BaseScreen<BaseOverlayUI, BattlePresenter> implements BattleView {

    private final ValueField<BattleParticipantData, Integer> userField;
    private final EnemyValueField enemyField;

    private final BlockableSkillsList<ActiveSkill> userActiveSkills;

    private final ValueField<Integer, Integer> aliveEnemiesField;
    private final ValueField<Void, Integer> userManaAmountField;

    private final Label roundLabel;

    private final AssetFactory<String, TextureRegion> imageFactory;
    private final LocalizedStringFactory localizedStringFactory;

    public BattleScreen(final UserBattleAPI user,
                        final Skin skin,
                        final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                        final DatabaseRepository databaseRepository, final BaseOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);

        presenter = new BattlePresenter(this,
                new BattleInteractor(user, databaseRepository),
                Gdx.app::postRunnable);

        this.userField = ValueFieldFactory.battleParticipantValueField(skin,
                TransformFactory.autoTransform(),
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory(),
                -200, 1);

        this.enemyField = new EnemyValueField(skin,
                TransformFactory.autoTransform(),
                factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory());

        this.userActiveSkills = UIElementFactory.applicableSkillsList(skin,
                GameConstants.MAX_CHOSEN_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                factoryStorage.getImageFactory(), this::onSkillClick);

        this.aliveEnemiesField = ValueFieldFactory.aliveEnemiesValueField(skin, UI_SKIN.BACKGROUND_COLOR.BROWN,
                TransformFactory.autoTransform());

        this.userManaAmountField = ValueFieldFactory.textValueField(skin, TransformFactory.autoTransform());
        presenter.addObserver_userManaAmount(userManaAmountField);

        this.roundLabel = new Label("", skin);

        this.imageFactory = factoryStorage.getImageFactory();
        this.localizedStringFactory = factoryStorage.getLocalizedStringFactory();
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
    public void cleanEnemies() {
        enemyField.clear();
    }

    @Override
    public void setEnemyCount(int count) {
        aliveEnemiesField.setInitialData(count);
    }

    @Override
    public void setRound(int round) {
        roundLabel.setText(localizedStringFactory.format(ROUND_KEY, round));
    }

    @Override
    public void updateDurationCoefficients(int castTime, int cooldown) {
        userActiveSkills.setDurationCoefficients(castTime, cooldown);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        userActiveSkills.update(delta);

        presenter.updateBattle(delta);
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

        layer.top();
        Table participants = new Table();
        participants.add(userField.build()).uniformX().expandX();
        participants.add(enemyField.build()).uniformX().expandX();
        layer.add(participants).fillX().row();

        Table bottomPart = new Table();
        bottomPart.bottom();

        int screenWidth = SCREEN_WIDTH();
        Table battleUtility = new Table();

        Table changeEnemySection = new Table();
        changeEnemySection.center();
        changeEnemySection.add(UIElementFactory.getButton(skin,
                localizedStringFactory.get(CHANGE_ENEMY_KEY), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeActiveEnemy();
            }
        })).padRight(20);
        changeEnemySection.add(aliveEnemiesField.build()).padRight(20);
        battleUtility.add(changeEnemySection).right().colspan(3).expandX().row();

        Table battleInfoSection = new Table();
        battleInfoSection.add(userManaAmountField.build()).center().uniformX().expandX();
        battleInfoSection.add(roundLabel).center().uniformX().expandX();
        battleInfoSection.add(UIElementFactory.getButton(skin,
                localizedStringFactory.get(LEAVE_KEY),
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        setScreen(ScreenInstance.MAIN_MENU, null);
                    }
                })).right().uniformX().expandX();
        battleUtility.add(battleInfoSection).center().growX();

        bottomPart.add(battleUtility).bottom().width(screenWidth).row();

        bottomPart.add(userActiveSkills.build()).padTop(10)
                .height(ACTIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).bottom().row();

        layer.add(bottomPart).growY().fillX();

        return layer;
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills) {
        userActiveSkills.setItems(activeSkills);
    }

    @Override
    public Consumer<? super Integer> setUser(BattleParticipantData userData) {
        userField.setInitialData(userData);
        return userField;
    }

    @Override
    public Consumer<? super Integer> addEnemy(BattleParticipantData enemyData) {
        Consumer<? super Integer> enemyObserver = enemyField.addEnemy(enemyData);
        return (x) -> {
            enemyObserver.accept(x);
            aliveEnemiesField.accept(x);
        };
    }

    @Override
    public void setActiveEnemy(int index) {
        enemyField.setActiveEnemy(index);
    }

    @Override
    public void startBattle(int enemiesNumber) {
        aliveEnemiesField.setInitialData(enemiesNumber);
    }

    @Override
    public void finishBattle(BattleSummaryData summaryData) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("BattleSummaryData", summaryData);
        setScreen(ScreenInstance.BATTLE_SUMMARY, arguments);
    }

    private void changeActiveEnemy() {
        presenter.changeActiveEnemy();
    }

    private void onSkillClick(ActiveSkill skill, int index) {
        presenter.applyUserSkill(skill, index);
    }
}
