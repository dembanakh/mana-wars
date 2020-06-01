package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.mana_wars.model.GameConstants;
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


import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mana_wars.ui.UIStringConstants.*;
import static com.mana_wars.ui.screens.util.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;
import static com.mana_wars.ui.screens.util.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;

class BattleScreen extends BaseScreen implements BattleView {

    private final Skin skin;

    private final Label testLabel;

    private final BattlePresenter presenter;

    private final List2D<ActiveSkill> userActiveSkills;

    BattleScreen(ScreenManager screenManager, FactoryStorage factoryStorage,
              RepositoryStorage repositoryStorage, OverlayUI overlayUI) {
        super(screenManager, factoryStorage, repositoryStorage, overlayUI);
        presenter = new BattlePresenter(this,
                new BattleInteractor(repositoryStorage.getLocalUserDataRepository(),
                        repositoryStorage.getDatabaseRepository()),
                Gdx.app::postRunnable);

        skin = factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING);
        testLabel = new Label("TEXT", skin);
        testLabel.setFontScale(2);
        userActiveSkills = new BattleSkillsList2D(skin, GameConstants.USER_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory()) {
            @Override
            protected void onSkillClick(ActiveSkill skill) {
                presenter.applySkill(skill);
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
    protected Skin getSkin() {
        return skin;
    }

    @Override
    protected void rebuildStage() {
        // layers
        Table layerBackground = buildBackgroundLayer(skin);
        Table layerForeground = buildForegroundLayer(skin);

        // fill stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(layerBackground);
        stack.add(layerForeground);
    }
    

    @Override
    public void show() {
        super.show();
        presenter.initBattle(new PvEBattle(new User(),new FirstDungeonEnemyFactory())); // start Battle as OnComplete initBattle
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    //TODO delete , only for the temporary use
    ActiveSkill userSkill;



    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        TextButton testButton = UIElementFactory.getButton(skin, "Use Skill",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (isBattle.get()){
                            presenter.applySkill(userSkill);
                        }
                    }
                });
        TextButton backButton = UIElementFactory.getButton(skin, "BACK",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        screenManager.setScreen(ScreenManager.ScreenInstance.MAIN_MENU);
                    }
                });

        userActiveSkills.getSelection().setMultiple(false);
        userActiveSkills.getSelection().setRequired(false);

        layer.add(testButton).pad(100).row();
        layer.add(testLabel).pad(100).row();
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
        testLabel.setText(text);
    }

    @Override
    public void setSkills(List<ActiveSkill> activeSkills, List<Skill> passiveSkills) {
        userActiveSkills.setItems(activeSkills);

        this.userSkill = activeSkills.get(0);
    }

    @Override
    public void finishBattle() {
        isBattle.set(false);
        testLabel.setText("Battle ended");
    }
}
