package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
import com.mana_wars.model.entity.skills.ActiveSkill;

import io.reactivex.functions.Consumer;

public interface BattleView extends BaseView {
    void setActiveEnemy(int index);
    void setSkills(Iterable<ActiveSkill> activeSkills);
    Consumer<? super Integer> setUser(BattleParticipantData userData);
    Consumer<? super Integer> addEnemy(BattleParticipantData enemyData);
    void blockSkills(int appliedSkillIndex);
    void startBattle(int enemiesNumber);
    void finishBattle(ReadableBattleSummaryData summaryData);
    void cleanEnemies();
    void setEnemyCount(int count);
    void setRound(int round);
    void updateDurationCoefficients(int castTime, int cooldown);
}
