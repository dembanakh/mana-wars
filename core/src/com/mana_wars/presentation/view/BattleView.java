package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.skills.ActiveSkill;

import io.reactivex.functions.Consumer;

public interface BattleView extends BaseView {
    void setActiveEnemy(int index);
    void setSkills(Iterable<ActiveSkill> activeSkills);
    void setUser(BattleParticipantData userData, Consumer<Consumer<? super Integer>> subscribe);
    void addEnemy(BattleParticipantData enemyData, Consumer<Consumer<? super Integer>> subscribe);
    void blockSkills(int appliedSkillIndex);
    void startBattle(int enemiesNumber);
    void finishBattle(BattleSummaryData summaryData);
    void cleanEnemies(int enemySize);
    void setRound(int round);
    void updateDurationCoefficients(int castTime, int cooldown);
}
