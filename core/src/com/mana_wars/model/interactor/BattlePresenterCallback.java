package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

public interface BattlePresenterCallback {

    void setSkills(List<ActiveSkill> activeSkills, List<Skill> passiveSkills);
    void setOpponents(List<BattleParticipant> userSide, List<BattleParticipant> enemySide);

    void startBattle();
}
