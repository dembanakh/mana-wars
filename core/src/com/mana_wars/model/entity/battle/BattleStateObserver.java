package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.List;

public interface BattleStateObserver {
    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setOpponents(BattleParticipant user, List<BattleParticipant> enemySide);

    void setCurrentRound(int round);
    void setEnemies(List<BattleParticipant> enemySide, int userTarget);
    void updateDurationCoefficients(int castTime, int cooldown);
}
