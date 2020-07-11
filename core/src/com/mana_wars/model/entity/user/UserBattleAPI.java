package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.skills.ActiveSkill;

import io.reactivex.subjects.Subject;

public interface UserBattleAPI extends UserBaseAPI{
    boolean tryApplyActiveSkill(int skillIndex);

    Subject<Integer> getManaAmountObservable();
    Iterable<ActiveSkill> getActiveSkills();
}
