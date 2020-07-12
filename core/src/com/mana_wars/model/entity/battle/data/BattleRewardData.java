package com.mana_wars.model.entity.battle.data;

public class BattleRewardData {

    private int manaReward;
    private int experienceReward;
    private int caseProbabilityReward;

    public BattleRewardData(int manaReward, int experienceReward, int caseProbabilityReward) {
        this.manaReward = manaReward;
        this.experienceReward = experienceReward;
        this.caseProbabilityReward = caseProbabilityReward;
    }

    public void add(BattleRewardData other) {
        this.manaReward += other.manaReward;
        this.experienceReward += other.experienceReward;
        this.caseProbabilityReward += caseProbabilityReward;
    }

    public int getManaReward() {
        return manaReward;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public int getCaseProbabilityReward() {
        return caseProbabilityReward;
    }
}
