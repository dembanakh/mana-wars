package com.mana_wars.model.entity.skills;

public interface ImmutableBattleSkill {
    boolean isAvailableAt(double currentTime);
    ActiveSkill getSkill();
}
