package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.List;

public interface BattleInitializationObserver {
    void onStartBattle();
    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide, List<BattleParticipant> enemySide);
}
