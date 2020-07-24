package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.battle.data.ReadableBattleStatisticsData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.Map;
import java.util.Set;

public interface BattleSummaryView extends BaseView {
    void setManaReward(int manaReward);
    void setExperienceReward(int experienceReward);
    void setSkillCasesReward(int skillCasesReward);
    void setParticipantsStatistics(Iterable<? extends Map.Entry<BattleParticipant,
            ? extends ReadableBattleStatisticsData>> participantsStatistics);
    void setBattleDuration(double battleDuration);
}
