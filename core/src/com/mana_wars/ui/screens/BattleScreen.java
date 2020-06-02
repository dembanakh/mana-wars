package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.mana_wars.model.GameConstants;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.overlays.OverlayUI;
import com.mana_wars.ui.screens.util.HealthField;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.battle.PvEBattle;
import com.mana_wars.model.entity.enemy.FirstDungeonEnemyFactory;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.presentation.presenters.BattlePresenter;
import com.mana_wars.presentation.view.BattleView;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.BattleSkillsList2D;
import com.mana_wars.ui.widgets.List2D;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIStringConstants.*;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;

public class BattleScreen extends BaseScreen implements BattleView {

    private final Skin skin;
    private final OverlayUI overlayUI;

    private final Label userHealthLabel;

    private final BattlePresenter presenter;

    //TODO temporary
    private HealthField userHealthField;
    private HealthField enemyHealthField;

    private final List2D<ActiveSkill> userActiveSkills;

    public BattleScreen(ScreenManager screenManager, FactoryStorage factoryStorage,
                        RepositoryStorage repositoryStorage, OverlayUI overlayUI) {
        super(screenManager);
        this.overlayUI = overlayUI;

        presenter = new BattlePresenter(this,
                new BattleInteractor(repositoryStorage.getLocalUserDataRepository(),
                        repositoryStorage.getDatabaseRepository()),
                Gdx.app::postRunnable);

        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
        userHealthLabel = new Label("TEXT", skin);
        userHealthLabel.setFontScale(2);

        userHealthField = new HealthField(100,50);
        enemyHealthField = new HealthField(100,100);

        userActiveSkills = new BattleSkillsList2D(skin, GameConstants.USER_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory()) {
            @Override
            protected void onSkillClick(ActiveSkill skill) {
                if (isBattle.get()) {
                    presenter.applySkill(skill);
                }
            }
        };
    }

    private AtomicBoolean isBattle = new AtomicBoolean(false);

    @Override
    public void startBattle() {
        isBattle.set(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(isBattle.get()){
            presenter.updateBattle(delta);
        }
    }

    @Override
    public void init() {

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
        addActor(userHealthField.build(skin));
        addActor(enemyHealthField.build(skin));
    }
    

    @Override
    public void show() {
        userHealthField.init();
        enemyHealthField.init();
        super.show();
        presenter.initBattle(new PvEBattle(new User(),new FirstDungeonEnemyFactory()),
                userHealthField, enemyHealthField); // start Battle as OnComplete initBattle
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

        TextButton testButton = UIElementFactory.getButton(skin, "Use Skill",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (isBattle.get()){
                        }
                    }
                });
        TextButton backButton = UIElementFactory.getButton(skin, "BACK",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
                    }
                });

        userActiveSkills.getSelection().setMultiple(false);
        userActiveSkills.getSelection().setRequired(false);

        layer.add(testButton).pad(100).row();
        layer.add(userHealthLabel).pad(100).row();
        layer.add(backButton).pad(100).row();
        layer.add(userActiveSkills).bottom().expandX().height(ACTIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();
        return layer;
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        presenter.dispose();
    }

    @Override
    public void setLabelText(String text) {
        userHealthLabel.setText(text);
    }

    @Override
    public void setSkills(List<ActiveSkill> activeSkills, List<Skill> passiveSkills) {
        userActiveSkills.setItems(activeSkills);
    }

    @Override
    public void finishBattle() {
        isBattle.set(false);
        userHealthLabel.setText("Battle ended");
    }
}
