package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;

public interface UserBattleAPI {
    BattleParticipant prepareBattleParticipant();
    boolean tryApplyActiveSkill(int skillIndex);
}
