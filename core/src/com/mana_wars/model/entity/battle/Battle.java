package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;

public interface Battle {
    void requestSkillApplication(BattleParticipant participant, ActiveSkill skill);
    void requestSkillApplication(BattleParticipant participant, ActiveSkill skill, double activationTime);
}
