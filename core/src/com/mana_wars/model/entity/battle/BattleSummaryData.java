package com.mana_wars.model.entity.battle;

public class BattleSummaryData {

    private final BattleRewardData rewardData = new BattleRewardData(0,0,0);

    public BattleSummaryData() {

    }

    public void addReward(BattleRewardData rewardData) {
        this.rewardData.add(rewardData);
    }

    public void combineWith(BattleSummaryData other) {
        this.rewardData.add(other.rewardData);
    }

    public BattleRewardData getRewardData() {
        return rewardData;
    }
}
