package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.data.ReadableBattleStatisticsData;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

import java.util.Map;
import java.util.Random;

public final class BattleSummaryInteractor extends BaseInteractor<UserBattleSummaryAPI> {

    private static Random Random = new Random();

    private ReadableBattleSummaryData summaryData;
    private int gainedSkillCases;

    public BattleSummaryInteractor(UserBattleSummaryAPI user){
        super(user);
    }

    public void parseSummaryData(ReadableBattleSummaryData summaryData) {
        this.summaryData = summaryData;

        //TODO just for testing
        System.out.println("Battle Stats");
        for (Map.Entry<BattleParticipant, ? extends ReadableBattleStatisticsData> entry :
                summaryData.getParticipantsStatistics().entrySet()) {
            ReadableBattleStatisticsData bsd = entry.getValue();
            System.out.println(entry.getKey().getData().name +
                    ":\ncaused damage="+ bsd.getCausedDamage() +
                    "\nreceived damage=" + bsd.getReceivedDamage() +
                    "\nself heal=" + bsd.getSelfHealing() +
                    "\nreceived heal=" + bsd.getReceivedHealing()
            );
        }

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
}
