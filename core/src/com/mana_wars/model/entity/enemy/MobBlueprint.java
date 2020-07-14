package com.mana_wars.model.entity.enemy;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

public class MobBlueprint {

    private final String name;
    private final String iconID;
    private final int initialHealth;
    private final Iterable<ActiveSkill> activeSkills;
    private final Iterable<PassiveSkill> passiveSkills;
    private final int manaReward;
    private final int experienceReward;
    private final int caseProbabilityReward;

    public MobBlueprint(String name, String iconID, int initialHealth,
                        Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills,
                        int manaReward, int experienceReward, int caseProbabilityReward) {
        this.name = name;
        this.iconID = iconID;
        this.initialHealth = initialHealth;
        this.activeSkills = activeSkills;
        this.passiveSkills = passiveSkills;
        this.manaReward = manaReward;
        this.experienceReward = experienceReward;
        this.caseProbabilityReward = caseProbabilityReward;
    }

    String getName() {
        return name;
    }

    String getIconID() {
        return iconID;
    }

    int getInitialHealth() {
        return initialHealth;
    }

    Iterable<ActiveSkill> getActiveSkills() {
        return activeSkills;
    }

    Iterable<PassiveSkill> getPassiveSkills() {
        return passiveSkills;
    }

    int getManaReward() {
        return manaReward;
    }

    int getExperienceReward() {
        return experienceReward;
    }

    int getCaseProbabilityReward() {
        return caseProbabilityReward;
    }
}
