package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;

public interface BattleInitializationObserver {
    void onStartBattle();
    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide);
}
