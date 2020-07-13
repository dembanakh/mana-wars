package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.PassiveSkill;

public class BattleParticipantData {
    public final String name;
    public final String iconID;
    public final int initialHealth;
    public final Iterable<PassiveSkill> passiveSkills;

    BattleParticipantData(String name, String iconID, int initialHealth, Iterable<PassiveSkill> passiveSkills) {
        this.name = name;
        this.iconID = iconID;
        this.initialHealth = initialHealth;
        this.passiveSkills = passiveSkills;
    }
}
