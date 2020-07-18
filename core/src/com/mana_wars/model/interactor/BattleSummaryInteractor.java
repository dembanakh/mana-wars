package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.data.BattleStatisticsData;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;

import java.util.Random;

public final class BattleSummaryInteractor extends BaseInteractor<UserBattleSummaryAPI> {

    private static Random Random = new Random();

    private BattleSummaryData summaryData;
    private int gainedSkillCases;

    public BattleSummaryInteractor(UserBattleSummaryAPI user){
        super(user);
    }

    public void parseSummaryData(BattleSummaryData summaryData) {
        this.summaryData = summaryData;

        //TODO just for testing
        System.out.println("Battle Stats");
        for (BattleParticipant bp : summaryData.getParticipantsStatistics().keySet()){

            BattleStatisticsData bsd = summaryData.getParticipantsStatistics().get(bp);
            System.out.println(bp.getData().name +
                    ":\ncaused damade="+ bsd.getCausedDamage() +
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
}
