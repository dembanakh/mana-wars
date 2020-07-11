package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

public final class BattleSummaryInteractor extends BaseInteractor<UserBattleSummaryAPI> {

    public BattleSummaryInteractor(UserBattleSummaryAPI user){
        super(user);
    }

    public void parseSummaryData(BattleSummaryData summaryData) {
        user.updateManaAmount(summaryData.getRewardData().getManaReward());
        user.updateExperience(summaryData.getRewardData().getExperienceReward());
    }
}
