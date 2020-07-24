package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.data.ReadableBattleStatisticsData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

import java.util.Map;
import java.util.Random;

public class BattleSummaryInteractor extends BaseInteractor<UserBattleSummaryAPI> {

    private static Random Random = new Random();

    private ReadableBattleSummaryData summaryData;
    private int gainedSkillCases;

    public BattleSummaryInteractor(UserBattleSummaryAPI user){
        super(user);
    }

    public void parseSummaryData(ReadableBattleSummaryData summaryData) {
        this.summaryData = summaryData;
        user.updateManaAmount(getManaReward());
        user.updateExperience(getExperienceReward());
        computeGainedSkillCases();
        user.updateSkillCases(getGainedSkillCases());
    }

    private void computeGainedSkillCases() {
        gainedSkillCases = 0;

        double casesCountProbability = summaryData.getRewardData().getCaseProbabilityReward() / 100d;

        gainedSkillCases = (int)Math.floor(casesCountProbability);
        casesCountProbability -= gainedSkillCases;

        if (Random.nextDouble() <= casesCountProbability) gainedSkillCases++;
    }

    public int getManaReward() {
        return summaryData.getRewardData().getManaReward();
    }

    public int getExperienceReward() {
        return summaryData.getRewardData().getExperienceReward();
    }

    public int getGainedSkillCases() {
        return gainedSkillCases;
    }

    public Map<BattleParticipant, ? extends ReadableBattleStatisticsData> getParticipantsStatistics() {
        return summaryData.getParticipantsStatistics();
    }

    public double getBattleDuration() {
        return summaryData.getTime();
    }
}
