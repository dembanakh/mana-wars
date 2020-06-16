package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import java.util.List;

public interface UserDungeonsAPI {
    BattleParticipant prepareBattleParticipant();
    void initSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills);

    int getLevel();
}
