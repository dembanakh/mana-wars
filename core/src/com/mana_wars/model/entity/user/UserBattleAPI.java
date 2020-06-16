package com.mana_wars.model.entity.user;

import io.reactivex.subjects.Subject;

public interface UserBattleAPI {
    boolean tryApplyActiveSkill(int skillIndex);
    Subject<Integer> getManaAmountObservable();
}
