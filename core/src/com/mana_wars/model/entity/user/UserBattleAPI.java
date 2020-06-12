package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;

import io.reactivex.subjects.Subject;

public interface UserBattleAPI {
    BattleParticipant prepareBattleParticipant();
    boolean tryApplyActiveSkill(int skillIndex);
    Subject<Integer> getManaAmountObservable();
}
