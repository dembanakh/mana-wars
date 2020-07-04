package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;

import java.util.List;

public interface Battle {
    List<BattleParticipant> getOpponents(BattleParticipant participant);
    void requestSkillApplication(BattleParticipant participant, ActiveSkill skill, double castTime);
}
