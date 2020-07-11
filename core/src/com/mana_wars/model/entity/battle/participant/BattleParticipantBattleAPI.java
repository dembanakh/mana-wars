package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.List;

public interface BattleParticipantBattleAPI {
    List<BattleParticipant> getOpponents(BattleParticipant participant);
    void requestSkillApplication(BattleParticipant participant, ActiveSkill skill, double castTime);
}
