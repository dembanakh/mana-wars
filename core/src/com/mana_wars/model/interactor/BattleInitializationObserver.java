package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

public interface BattleInitializationObserver {

    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide);
    void onStartBattle();

}
