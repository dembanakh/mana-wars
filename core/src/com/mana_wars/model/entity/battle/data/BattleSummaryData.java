package com.mana_wars.model.entity.battle.data;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.HashMap;
import java.util.Map;

public class BattleSummaryData implements ReadableBattleSummaryData {

    private final double time;
    private final BattleRewardData rewardData = new BattleRewardData();
    private final Map<BattleParticipant, BattleStatisticsData> participantsStatistics = new HashMap<>();

    public BattleSummaryData(double time) {
        this.time = time;
    }

    public void addReward(BattleRewardData rewardData) {
        this.rewardData.add(rewardData);
    }

    public void addStatisticsFrom(BattleParticipant bp){
        if (!participantsStatistics.containsKey(bp)){
            participantsStatistics.put(bp, bp.getBattleStatisticsData());
        }
    }

    public void combineWith(ReadableBattleSummaryData other) {
        rewardData.add(other.getRewardData());
        for (BattleParticipant bp : other.getParticipantsStatistics().keySet()){
            addStatisticsFrom(bp);
        }
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public ReadableBattleRewardData getRewardData() {
        return rewardData;
    }

    @Override
    public Map<BattleParticipant, ? extends ReadableBattleStatisticsData> getParticipantsStatistics() {
        return participantsStatistics;
    }
}
