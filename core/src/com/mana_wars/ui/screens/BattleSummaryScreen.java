package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.battle.data.ReadableBattleStatisticsData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;
import com.mana_wars.model.interactor.BattleSummaryInteractor;
import com.mana_wars.presentation.presenters.BattleSummaryPresenter;
import com.mana_wars.presentation.view.BattleSummaryView;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.widgets.StatisticsTable;

import java.util.Locale;
import java.util.Map;

import static com.mana_wars.ui.UIStringConstants.BATTLE_SUMMARY_SCREEN.BATTLE_FINISHED_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SUMMARY_SCREEN.CASES_REWARD_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SUMMARY_SCREEN.MANA_REWARD_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SUMMARY_SCREEN.TO_MAIN_MENU_KEY;
import static com.mana_wars.ui.UIStringConstants.BATTLE_SUMMARY_SCREEN.XP_REWARD_KEY;

public final class BattleSummaryScreen extends BaseScreen<BaseOverlayUI, BattleSummaryPresenter>
        implements BattleSummaryView {

    private final Label battleDurationLabel;
    private final Label manaRewardLabel;
    private final Label xpRewardLabel;
    private final Label skillCasesRewardLabel;

    private final StatisticsTable statisticsTable;

    private final LocalizedStringFactory localizedStringFactory;

    public BattleSummaryScreen(final UserBattleSummaryAPI user,
                               final Skin skin,
                               final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                               final RepositoryStorage repositoryStorage, final BaseOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);

        presenter = new BattleSummaryPresenter(this,
                new BattleSummaryInteractor(user),
                Gdx.app::postRunnable);
        this.battleDurationLabel = new Label("", skin);
        this.manaRewardLabel = new Label("", skin);
        this.xpRewardLabel = new Label("", skin);
        this.skillCasesRewardLabel = new Label("", skin);
        this.statisticsTable = new StatisticsTable(skin);
        this.localizedStringFactory = factoryStorage.getLocalizedStringFactory();
    }

    @Override
    public BaseScreen reInit(Map<String, Object> arguments) {
        ReadableBattleSummaryData summaryData = getArgument(arguments, "BattleSummaryData");
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
        layer.setFillParent(true);

        layer.add(new Label(localizedStringFactory.get(BATTLE_FINISHED_KEY), skin)).padBottom(100).row();

        layer.add(battleDurationLabel).row();
        layer.add(manaRewardLabel).row();
        layer.add(xpRewardLabel).row();
        layer.add(skillCasesRewardLabel).row();

        layer.add(statisticsTable.build()).padTop(50).growX().row();

        layer.add(UIElementFactory.getButton(skin,
                localizedStringFactory.get(TO_MAIN_MENU_KEY), new ChangeListener() {
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
        manaRewardLabel.setText(localizedStringFactory.format(MANA_REWARD_KEY, manaReward));
    }

    @Override
    public void setExperienceReward(int experienceReward) {
        xpRewardLabel.setText(localizedStringFactory.format(XP_REWARD_KEY, experienceReward));
    }

    @Override
    public void setSkillCasesReward(int skillCasesReward) {
        skillCasesRewardLabel.setText(localizedStringFactory.format(CASES_REWARD_KEY, skillCasesReward));
    }

    @Override
    public void setParticipantsStatistics(Iterable<? extends Map.Entry<BattleParticipant, ? extends ReadableBattleStatisticsData>> participantsStatistics) {
        statisticsTable.clear();
        for (Map.Entry<BattleParticipant, ? extends ReadableBattleStatisticsData> entry : participantsStatistics) {
            statisticsTable.add(entry.getKey().getData().name,
                    entry.getValue());
        }
        statisticsTable.showDefault();
    }

    @Override
    public void setBattleDuration(double battleDuration) {
        battleDurationLabel.setText(String.format(Locale.US, "%.2f", battleDuration));
    }
}
