package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;
import com.mana_wars.model.interactor.BattleSummaryInteractor;
import com.mana_wars.presentation.presenters.BattleSummaryPresenter;
import com.mana_wars.presentation.view.BattleSummaryView;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

import java.util.Map;

public final class BattleSummaryScreen extends BaseScreen<BaseOverlayUI, BattleSummaryPresenter>
        implements BattleSummaryView {

    private final Label manaRewardLabel;
    private final Label xpRewardLabel;
    private final Label skillCasesRewardLabel;

    public BattleSummaryScreen(final UserBattleSummaryAPI user,
                               final Skin skin,
                               final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                               final RepositoryStorage repositoryStorage, final BaseOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);

        presenter = new BattleSummaryPresenter(this,
                new BattleSummaryInteractor(user),
                Gdx.app::postRunnable);
        manaRewardLabel = new Label("", skin);
        xpRewardLabel = new Label("", skin);
        skillCasesRewardLabel = new Label("", skin);
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        BattleSummaryData summaryData = getArgument(arguments, "BattleSummaryData");
        presenter.parseSummaryData(summaryData);
        return this;
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();

        layer.add(new Label("BATTLE FINISHED", skin)).padBottom(100).row();

        layer.add(manaRewardLabel).row();
        layer.add(xpRewardLabel).row();
        layer.add(skillCasesRewardLabel).row();

        layer.add(UIElementFactory.getButton(skin, "TO MAIN MENU", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMain();
            }
        })).padTop(100);

        return layer;
    }

    private void onMain() {
        setScreen(ScreenInstance.MAIN_MENU, null);
    }

    @Override
    public void setManaReward(int manaReward) {
        manaRewardLabel.setText("MANA REWARD: " + manaReward);
    }

    @Override
    public void setExperienceReward(int experienceReward) {
        xpRewardLabel.setText("XP REWARD: " + experienceReward);
    }

    @Override
    public void setSkillCasesReward(int skillCasesReward) {
        skillCasesRewardLabel.setText("SKILL CASES: " + skillCasesReward);
    }
}
