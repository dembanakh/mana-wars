package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.List;

public interface BattleStateObserver {
    void onStartBattle();
    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide,
                      List<BattleParticipant> enemySide);

    void setCurrentRound(int round);
    void setEnemies(BattleParticipant user, List<BattleParticipant> enemySide);
    void updateDurationCoefficients(int castTime, int cooldown);
}
