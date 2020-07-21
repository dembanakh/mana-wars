package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
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
import com.mana_wars.ui.widgets.ChangeEnemyButton;
import com.mana_wars.ui.widgets.skills_list_2d.BlockableSkillsList;
import com.mana_wars.ui.widgets.value_field.EnemyValueField;
import com.mana_wars.ui.widgets.value_field.ValueFieldFactory;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.mana_wars.model.GameConstants.CHOSEN_BATTLE_BUILDER;
import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SCREEN.LEAVE_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SCREEN.ROUND_KEY;
import static com.mana_wars.ui.UIStringConstants.UI_SKIN;

public final class BattleScreen extends BaseScreen<BaseOverlayUI, BattlePresenter> implements BattleView {

    private final ValueField<BattleParticipantData, Integer> userField;
    private final EnemyValueField enemyField;

    private final BlockableSkillsList<ActiveSkill> userActiveSkills;

    private final ValueField<Integer, Integer> aliveEnemiesField;
    private final ValueField<Void, Integer> userManaAmountField;

    private final Container<Label> roundLabel;
    private final ChangeEnemyButton changeEnemyButton;

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

        this.imageFactory = factoryStorage.getImageFactory();
        this.localizedStringFactory = factoryStorage.getLocalizedStringFactory();

        this.roundLabel = new Container<>(new Label("", skin));
        roundLabel.setTransform(true);
        roundLabel.setOrigin(Align.center);
        roundLabel.getActor().setOrigin(Align.center);
        this.changeEnemyButton = new ChangeEnemyButton(skin, localizedStringFactory, this::changeActiveEnemy);
    }

    @Override
    public void blockSkills(int appliedSkillIndex) {
        userActiveSkills.blockSkills(appliedSkillIndex);
        changeEnemyButton.block(appliedSkillIndex);
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
        roundLabel.getActor().setText(localizedStringFactory.format(ROUND_KEY, round));

        Vector2 initialPosition = new Vector2(roundLabel.getX(Align.center), roundLabel.getY(Align.center));
        roundLabel.setPosition(SCREEN_WIDTH() / 2f, SCREEN_HEIGHT() / 2f, Align.center);
        roundLabel.setScale(3f);
        roundLabel.addAction(Actions.sequence(
                Actions.parallel(
                    Actions.moveToAligned(initialPosition.x, initialPosition.y, Align.center,
                            1f, Interpolation.bounceOut),
                    Actions.scaleBy(-2f, -2f, 1f, Interpolation.linear)
        )));
    }

    @Override
    public void updateDurationCoefficients(int castTime, int cooldown) {
        userActiveSkills.setDurationCoefficients(castTime, cooldown);
        changeEnemyButton.setCastTimeCoefficient(castTime);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        userActiveSkills.update(delta);
        changeEnemyButton.update(delta);

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
        changeEnemySection.add(changeEnemyButton.build()).padRight(20);
        changeEnemySection.add(aliveEnemiesField.build()).padRight(20).fill();
        battleUtility.add(changeEnemySection).right().colspan(3).expandX().row();

        Table battleInfoSection = new Table();
        battleInfoSection.add(userManaAmountField.build()).center().uniformX().expandX();
        battleInfoSection.add(roundLabel).uniformX().expandX();
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

        System.out.println(roundLabel.getX());

        return layer;
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills) {
        userActiveSkills.setItems(activeSkills);
        changeEnemyButton.setSkills(activeSkills);
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
    public void finishBattle(ReadableBattleSummaryData summaryData) {
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
