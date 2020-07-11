package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleSummaryData;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

public class BattleSummaryInteractor extends BaseInteractor {

    private final UserBattleSummaryAPI user;

    public BattleSummaryInteractor(UserBattleSummaryAPI user){
        this.user = user;
    }

    public void parseSummaryData(BattleSummaryData summaryData) {
        user.updateManaAmount(summaryData.getRewardData().getManaReward());
        user.updateExperience(summaryData.getRewardData().getExperienceReward());
    }
}
