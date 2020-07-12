package com.mana_wars.model.entity.battle.participant;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.data.BattleRewardData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BattleParticipant {

    //Target
    private static Random random = new Random();
    private int currentTarget = -1;

    private BattleParticipantBattleAPI battleParticipantBattleAPI;

    private final String name;
    private final BattleParticipantCharacteristics characteristics;
    private final BattleRewardData onDeathReward;

    protected final List<BattleSkill> battleSkills = new ArrayList<>();
    protected final Iterable<PassiveSkill> passiveSkills;

    private final Subject<Integer> healthObservable;

    public BattleParticipant(String name, int initialHealth, Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills, int manaReward, int experienceReward, int caseProbabilityReward) {
        this(name, initialHealth, new ArrayList<>(), passiveSkills,
                new BattleRewardData(manaReward,experienceReward,caseProbabilityReward));

        for (ActiveSkill s : activeSkills) {
            battleSkills.add(new BattleSkill(s));
        }
    }

    protected BattleParticipant(String name, int initialHealth, List<BattleSkill> battleSkills, Iterable<PassiveSkill> passiveSkills, BattleRewardData onDeathReward) {
        this.name = name;

        this.characteristics = new BattleParticipantCharacteristics(initialHealth);

        this.passiveSkills = passiveSkills;
        for (BattleSkill skill : battleSkills) {
            this.battleSkills.add(new BattleSkill(skill.skill));
        }
        healthObservable = BehaviorSubject.create();
        this.onDeathReward = onDeathReward;
    }

    public abstract void update(final double currentTime);

    public void start() {
        healthObservable.onNext(characteristics.getInitialHealth());
        changeTarget();
    }

    public int changeTarget(){
        List<BattleParticipant> opponents = battleParticipantBattleAPI.getOpponents(this);
        List<Integer> possibleAnotherTargets = new ArrayList<>();
        for (int i = 0, n = opponents.size(); i < n; i++) {
            if (i != currentTarget && opponents.get(i).isAlive()){
                possibleAnotherTargets.add(i);
            }
        }

        if (possibleAnotherTargets.isEmpty())
            return currentTarget;

        currentTarget = possibleAnotherTargets.get(random.nextInt(possibleAnotherTargets.size()));
        return currentTarget;
    }

    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel) {
        characteristics.applySkillCharacteristic(sc,skillLevel);
        if (sc.isHealth()) healthObservable.onNext(characteristics.getCharacteristicValue(Characteristic.HEALTH));
    }

    protected synchronized void applySkill(ActiveSkill skill, double currentTime) {
        double castTime = skill.getCastTime(getCharacteristicValue(Characteristic.CAST_TIME));
        double cooldown = skill.getCooldown(getCharacteristicValue(Characteristic.COOLDOWN));
        battleParticipantBattleAPI.requestSkillApplication(this, skill, castTime);
        for (BattleSkill battleSkill : battleSkills) {
            battleSkill.updateAvailabilityTime(currentTime + castTime +
                    (battleSkill.skill == skill ? cooldown : 0));
        }
    }

    public void setBattleParticipantBattleAPI(BattleParticipantBattleAPI battleParticipantBattleAPI) {
        this.battleParticipantBattleAPI = battleParticipantBattleAPI;
    }

    public boolean isAlive() {
        return getCharacteristicValue(Characteristic.HEALTH) > 0;
    }

    protected int getInitialHealthAmount() {
        return characteristics.getInitialHealth();
    }

    public Iterable<PassiveSkill> getPassiveSkills() {
        return passiveSkills;
    }

    public Subject<Integer> getHealthObservable() {
        return healthObservable;
    }

    public String getName() {
        return name;
    }

    public int getCurrentTarget() {
        return currentTarget;
    }

    public int getCharacteristicValue(Characteristic type) {
        return characteristics.getCharacteristicValue(type);
    }

    public void setCharacteristicValue(Characteristic type, int value) {
        characteristics.setCharacteristicValue(type, value);
    }

    public BattleParticipantData getData() {
        return new BattleParticipantData(name, characteristics.getInitialHealth(), passiveSkills);
    }

    public BattleRewardData getOnDeathReward() {
        return onDeathReward;
    }

    public void disableManaConsumption() {
        characteristics.setConsumeMana(false);
    }

    public void enableManaConsumption() {
        characteristics.setConsumeMana(true);
    }
}
