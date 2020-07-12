package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

public final class BattleSummaryInteractor extends BaseInteractor<UserBattleSummaryAPI> {

    private BattleSummaryData summaryData;

    public BattleSummaryInteractor(UserBattleSummaryAPI user){
        super(user);
    }

    public void parseSummaryData(BattleSummaryData summaryData) {
        this.summaryData = summaryData;

        user.updateManaAmount(summaryData.getRewardData().getManaReward());
        user.updateExperience(summaryData.getRewardData().getExperienceReward());
        updateSkillCases();
    }

    private void updateSkillCases() {

    }

    public int getManaReward() {
        return summaryData.getRewardData().getManaReward();
    }

    public int getExperienceReward() {
        return summaryData.getRewardData().getExperienceReward();
    }
}
