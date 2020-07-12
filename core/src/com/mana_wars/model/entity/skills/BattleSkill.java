package com.mana_wars.model.entity.skills;

public class BattleSkill implements ImmutableBattleSkill {
    private double availabilityTime;
    private final ActiveSkill skill;

    public BattleSkill(ActiveSkill skill) {
        this.skill = skill;
        this.availabilityTime = 0;
    }

    public void updateAvailabilityTime(double possibleTime) {
        availabilityTime = Math.max(possibleTime, availabilityTime);
    }

    @Override
    public boolean isAvailableAt(double currentTime) {
        return Double.compare(currentTime, availabilityTime) >= 0;
    }

    @Override
    public ActiveSkill getSkill() {
        return skill;
    }
}
