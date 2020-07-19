package com.mana_wars.model.entity.battle.data;

public class BattleRewardData implements ReadableBattleRewardData {

    private int manaReward;
    private int experienceReward;
    private int caseProbabilityReward;

    public BattleRewardData(int manaReward, int experienceReward, int caseProbabilityReward) {
        this.manaReward = manaReward;
        this.experienceReward = experienceReward;
        this.caseProbabilityReward = caseProbabilityReward;
    }

    BattleRewardData() {
        this(0, 0, 0);
    }

    public void add(ReadableBattleRewardData other) {
        this.manaReward += other.getManaReward();
        this.experienceReward += other.getExperienceReward();
        this.caseProbabilityReward += other.getCaseProbabilityReward();
    }

    @Override
    public int getManaReward() {
        return manaReward;
    }

    @Override
    public int getExperienceReward() {
        return experienceReward;
    }

    @Override
    public int getCaseProbabilityReward() {
        return caseProbabilityReward;
    }
}
