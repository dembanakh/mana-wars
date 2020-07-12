package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
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

    private final BattleRewardData onDeathReward;
    private final Subject<Integer> healthObservable;

    public BattleParticipant(String name, int initialHealth, Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills, int manaReward, int experienceReward, int caseProbabilityReward) {
        this(name, initialHealth, null, passiveSkills,
                new BattleRewardData(manaReward, experienceReward, caseProbabilityReward));

        skills.add(activeSkills);
    }

    protected BattleParticipant(String name, int initialHealth, SkillsSet skills, Iterable<PassiveSkill> passiveSkills, BattleRewardData onDeathReward) {
        this.data = new BattleParticipantData(name, initialHealth, passiveSkills);
        this.currentTarget = new BattleParticipantTarget(this);
        this.characteristics = new BattleParticipantCharacteristics(initialHealth);
        setCharacteristicApplicator();
        this.skills = new BaseSkillsSet();
        this.onDeathReward = onDeathReward;

        if (skills != null) {
            for (SkillsSet.Entry skill : skills) {
                this.skills.add(skill.skill);
            }
        }
        healthObservable = BehaviorSubject.create();
    }

    public abstract void update(final double currentTime);

    public void start() {
        healthObservable.onNext(characteristics.getInitialHealth());
        changeTarget();
    }

    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel) {
        characteristics.applySkillCharacteristic(sc,skillLevel);
        if (sc.isHealth()) healthObservable.onNext(characteristics.getValue(Characteristic.HEALTH));
    }

    protected synchronized void applySkill(ActiveSkill skill, double currentTime) {
        double castTime = skill.getCastTime(getCharacteristicValue(Characteristic.CAST_TIME));
        double cooldown = skill.getCooldown(getCharacteristicValue(Characteristic.COOLDOWN));
        battleClientAPI.requestSkillApplication(this, skill, castTime);
        skills.onSkillApplied(skill, currentTime, castTime, cooldown);
    }

    //region Getters and Setters
    public void setCharacteristicApplicator(SkillCharacteristicApplicator applicator) {
        characteristics.setApplicator(applicator);
    }

    public void setCharacteristicApplicator() {
        setCharacteristicApplicator(new BaseSkillCharacteristicApplicator());
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

    protected int getInitialHealthAmount() {
        return characteristics.getInitialHealth();
    }

    public Iterable<PassiveSkill> getPassiveSkills() {
        return data.passiveSkills;
    }

    public Subject<Integer> getHealthObservable() {
        return healthObservable;
    }

    public String getName() {
        return data.name;
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

    public BattleParticipantData getData() {
        return data;
    }

    public BattleRewardData getOnDeathReward() {
        return onDeathReward;
    }
    //endregion
}
