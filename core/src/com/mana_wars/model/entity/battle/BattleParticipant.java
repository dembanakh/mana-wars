package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.ArrayList;
import java.util.EnumMap;
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
    private final int initialHealth;
    protected final Iterable<PassiveSkill> passiveSkills;

    private final BattleRewardData onDeathReward;

    protected final List<BattleSkill> battleSkills = new ArrayList<>();
    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);

    private final Subject<Integer> healthObservable;

    public BattleParticipant(String name, int initialHealth, Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills, int manaReward, int experienceReward, int caseProbabilityReward) {
        this(name, initialHealth, new ArrayList<BattleSkill>(), passiveSkills,
                new BattleRewardData(manaReward,experienceReward,caseProbabilityReward));

        for (ActiveSkill s : activeSkills) {
            battleSkills.add(new BattleSkill(s));
        }
    }

    protected BattleParticipant(String name, int initialHealth, List<BattleSkill> battleSkills, Iterable<PassiveSkill> passiveSkills, BattleRewardData onDeathReward) {
        this.name = name;
        this.initialHealth = initialHealth;
        setCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.HEALTH, initialHealth);
        setCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.MANA, 0);
        setCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.CAST_TIME, 100);
        setCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.COOLDOWN, 100);
        //game private data
        setCharacteristicValue(com.mana_wars.model.entity.base.Characteristic._MANA_COST, 100);

        this.passiveSkills = passiveSkills;
        for (BattleSkill skill : battleSkills) {
            this.battleSkills.add(new BattleSkill(skill.skill));
        }
        healthObservable = BehaviorSubject.create();
        this.onDeathReward = onDeathReward;
    }

    public abstract void update(final double currentTime);

    void start() {
        healthObservable.onNext(initialHealth);
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
        com.mana_wars.model.entity.base.Characteristic c = sc.getCharacteristic();


        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue(skillLevel));

        //TODO very strongly think about it!
        if(sc.isManaCost()){
            changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(),
                    sc.getValue(skillLevel) * getCharacteristicValue(com.mana_wars.model.entity.base.Characteristic._MANA_COST) / 100);
        }

        if (c == com.mana_wars.model.entity.base.Characteristic.HEALTH) {
            changedValue = Math.min(changedValue, initialHealth);
            healthObservable.onNext(changedValue);
        }
        setCharacteristicValue(c, changedValue);
    }

    protected synchronized void applySkill(ActiveSkill skill, double currentTime) {
        double castTime = skill.getCastTime(getCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.CAST_TIME));
        double cooldown = skill.getCooldown(getCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.COOLDOWN));
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
        return getCharacteristicValue(com.mana_wars.model.entity.base.Characteristic.HEALTH) > 0;
    }

    public int getInitialHealthAmount() {
        return initialHealth;
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

    public int getCharacteristicValue(com.mana_wars.model.entity.base.Characteristic type) {
        return characteristics.get(type);
    }

    protected void setCharacteristicValue(Characteristic type, int value) {
        characteristics.put(type, value);
    }

    public BattleParticipantData getData() {
        return new BattleParticipantData(name, initialHealth, passiveSkills);
    }


    public BattleRewardData getOnDeathReward() {
        return onDeathReward;
    }
}
