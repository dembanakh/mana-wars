package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import java.util.List;

import io.reactivex.Observable;

public interface UserBattleAPI extends UserBaseAPI{
    boolean tryApplyActiveSkill(ActiveSkill skill);

    Observable<Integer> getManaAmountObservable();
    Iterable<ActiveSkill> getActiveSkills();

    BattleParticipant prepareBattleParticipant(List<ActiveSkill> userActiveSkills,
                                               Iterable<PassiveSkill> userPassiveSkills);
}
