package com.mana_wars.model.entity.user;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import java.util.List;

public interface UserDungeonsAPI extends UserBaseAPI{
    int getLevel();
    int getManaAmount();
}
