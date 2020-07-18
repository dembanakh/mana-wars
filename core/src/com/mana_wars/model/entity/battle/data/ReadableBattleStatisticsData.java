package com.mana_wars.model.entity.battle.data;

public interface ReadableBattleStatisticsData {
    int getCausedDamage();
    int getReceivedDamage();
    int getSelfHealing();
    int getReceivedHealing();
}
