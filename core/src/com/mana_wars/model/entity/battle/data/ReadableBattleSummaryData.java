package com.mana_wars.model.entity.battle.data;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.Map;

public interface ReadableBattleSummaryData {
    double getTime();
    ReadableBattleRewardData getRewardData();
    Map<BattleParticipant, ? extends ReadableBattleStatisticsData> getParticipantsStatistics();
}
