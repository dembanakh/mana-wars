package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import java.util.List;

public interface UserDungeonsAPI {
    int getLevel();
    int getManaAmount();
    void initSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills);
    BattleParticipant prepareBattleParticipant();
}
