package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.data.BattleStatisticsData;
import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BattleParticipant {

    private BattleClientAPI battleClientAPI;

    private final BattleParticipantData data;
    private final BattleParticipantTarget currentTarget;
    protected final SkillsSet skills;
    private final BattleParticipantCharacteristics characteristics;

    private SkillCharacteristicApplicationMode applicator;

    private final BattleRewardData onDeathReward;
    private final BattleStatisticsData battleStatisticsData;
    private final Subject<Integer> healthObservable;

    public BattleParticipant(String name, String iconID, int initialHealth,
                             Iterable<? extends ActiveSkill> activeSkills,
                             Iterable<? extends PassiveSkill> passiveSkills,
                             int manaReward, int experienceReward, int caseProbabilityReward) {
        this.data = new BattleParticipantData(name, iconID, initialHealth, passiveSkills);
        this.currentTarget = new BattleParticipantTarget(this);
        this.characteristics = new BattleParticipantCharacteristics(initialHealth);
        setCharacteristicApplicationMode(SkillCharacteristicApplicationMode.DEFAULT);
        this.skills = new BaseSkillsSet(activeSkills);
        this.onDeathReward = new BattleRewardData(manaReward, experienceReward, caseProbabilityReward);
        this.battleStatisticsData = new BattleStatisticsData();
        healthObservable = BehaviorSubject.create();
    }

    public abstract void update(final double currentTime);

    public void start() {
        healthObservable.onNext(characteristics.getInitialHealth());
    }

    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel) {
        applicator.applySkillCharacteristic(sc, skillLevel, characteristics);
        if (sc.isHealth()) healthObservable.onNext(characteristics.getValue(Characteristic.HEALTH));
    }

    protected synchronized void applySkill(ActiveSkill skill, double currentTime) {
        double castTime = skill.getCastTime(getCharacteristicValue(Characteristic.CAST_TIME));
        double cooldown = skill.getCooldown(getCharacteristicValue(Characteristic.COOLDOWN));
        battleClientAPI.requestSkillApplication(this, skill, castTime);
        skills.onSkillApplied(skill, currentTime, castTime, cooldown);
    }

    //region Getters and Setters
    public void setCharacteristicApplicationMode(SkillCharacteristicApplicationMode applicationMode) {
        this.applicator = applicationMode;
    }

    public void setBattleClientAPI(BattleClientAPI battleClientAPI) {
        this.battleClientAPI = battleClientAPI;
    }

    public boolean isAlive() {
        return getCharacteristicValue(Characteristic.HEALTH) > 0;
    }

    public int changeTarget() {
        return currentTarget.change(battleClientAPI);
    }

    public Iterable<? extends PassiveSkill> getPassiveSkills() {
        return data.passiveSkills;
    }

    public Subject<Integer> getHealthObservable() {
        return healthObservable;
    }

    public int getCurrentTarget() {
        return currentTarget.get();
    }

    public int getCharacteristicValue(Characteristic type) {
        return characteristics.getValue(type);
    }

    public void setCharacteristicValue(Characteristic type, int value) {
        characteristics.setValue(type, value);
    }

    public BattleStatisticsData getBattleStatisticsData() {
        return battleStatisticsData;
    }

    public BattleParticipantData getData() {
        return data;
    }

    public BattleRewardData getOnDeathReward() {
        return onDeathReward;
    }
    //endregion
}
