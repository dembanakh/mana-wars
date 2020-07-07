package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.battle.BattleSummaryData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import io.reactivex.functions.Consumer;

public interface BattleView extends BaseView {
    void setActiveEnemy(int index);
    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setUser(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills,
                 Consumer<Consumer<? super Integer>> subscribe);
    void addEnemy(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills,
                  Consumer<Consumer<? super Integer>> subscribe);
    void blockSkills(int appliedSkillIndex);
    void startBattle(int enemiesNumber);
    void finishBattle(BattleSummaryData summaryData);
}
