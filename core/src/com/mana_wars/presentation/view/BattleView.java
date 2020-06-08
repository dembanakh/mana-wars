package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface BattleView {

    void setSkills(Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills);
    void setPlayers(User user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide);
    void finishBattle();
    void startBattle();

}
