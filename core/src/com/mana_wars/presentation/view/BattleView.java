package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

public interface BattleView extends BaseView{

    void setSkills(Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills);
    void setPlayers(BattleParticipant user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide);
    void finishBattle();

    void blockSkills(int appliedSkillIndex);

    void startBattle();

}
