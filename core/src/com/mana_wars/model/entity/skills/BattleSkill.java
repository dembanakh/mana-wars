package com.mana_wars.model.entity.skills;

public class BattleSkill {

    private double availabilityTime;
    public final ActiveSkill skill;

    public BattleSkill(ActiveSkill skill) {
        this.skill = skill;
        this.availabilityTime = 0;
    }

    public void updateAvailabilityTime(double possibleTime) {
        availabilityTime = Math.max(possibleTime, availabilityTime);
    }

    public double getAvailabilityTime() {
        return availabilityTime;
    }
}
