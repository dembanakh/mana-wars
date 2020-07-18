package com.mana_wars.model.entity.battle.data;

import com.mana_wars.model.entity.battle.participant.BattleParticipant;

import java.util.HashMap;
import java.util.Map;

public class BattleSummaryData {

    private final BattleRewardData rewardData = new BattleRewardData(0,0,0);
    private final Map<BattleParticipant, BattleStatisticsData> participantsStatistics = new HashMap<>();

    public void addReward(BattleRewardData rewardData) {
        this.rewardData.add(rewardData);
    }

    public void addStatisticsFrom(BattleParticipant bp){
        if (!participantsStatistics.containsKey(bp)){
            participantsStatistics.put(bp, bp.getBattleStatisticsData());
        }
    }

    public void combineWith(BattleSummaryData other) {
        this.rewardData.add(other.rewardData);
        for (BattleParticipant bp : other.participantsStatistics.keySet()){
            addStatisticsFrom(bp);
        }
    }

    public BattleRewardData getRewardData() {
        return rewardData;
    }
    public Map<BattleParticipant, BattleStatisticsData> getParticipantsStatistics(){
        return participantsStatistics;
    }
}
