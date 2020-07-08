package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.PassiveSkill;

public class BattleParticipantData {
    public final String name;
    public final int initialHealth;
    public final Iterable<PassiveSkill> passiveSkills;

    public BattleParticipantData(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills) {
        this.name = name;
        this.initialHealth = initialHealth;
        this.passiveSkills = passiveSkills;
    }
}
