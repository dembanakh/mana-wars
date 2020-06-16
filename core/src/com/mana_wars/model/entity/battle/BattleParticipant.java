package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BattleParticipant {

    protected Battle battle;

    private String name;
    protected final int initialHealth;

    protected final List<BattleSkill> battleSkills = new ArrayList<>();

    protected List<PassiveSkill> passiveSkills;
    private final Subject<Integer> healthObservable;

    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);

    public BattleParticipant(String name, int initialHealth) {
        this(initialHealth);
        setName(name);
    }

    public BattleParticipant(int initialHealth) {
        this.initialHealth = initialHealth;
        setCharacteristicValue(Characteristic.HEALTH, initialHealth);
        setCharacteristicValue(Characteristic.MANA, 0);
        setCharacteristicValue(Characteristic.CAST_TIME, 100);
        setCharacteristicValue(Characteristic.COOLDOWN, 100);
        healthObservable = BehaviorSubject.create();
    }

    public void start() {
        healthObservable.onNext(initialHealth);
    }

    public abstract void update(final double currentTime);

    public void applySkillCharacteristic(SkillCharacteristic sc) {
        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue());
        if (c == Characteristic.HEALTH) {
            healthObservable.onNext(changedValue);
            changedValue = Math.min(changedValue, initialHealth);
        }
        setCharacteristicValue(c, changedValue);
    }

    protected synchronized void applySkill(ActiveSkill skill, double currentTime) {
        double castTime = skill.getCastTime() * getCharacteristicValue(Characteristic.CAST_TIME) / 100;
        double cooldown = skill.getCooldown() * getCharacteristicValue(Characteristic.COOLDOWN) / 100;
        battle.requestSkillApplication(this, skill, castTime);
        for (BattleSkill battleSkill : battleSkills) {
            battleSkill.updateAvailabilityTime(currentTime + castTime +
                    (battleSkill.skill == skill ? cooldown : 0));
        }
    }

    protected void initSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills){
        this.passiveSkills = passiveSkills;

        for (ActiveSkill s : activeSkills) {
            battleSkills.add(new BattleSkill(s));
        }
    }


    protected void setName(final String name) {
        this.name = name;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    boolean isAlive() {
        return getCharacteristicValue(Characteristic.HEALTH) > 0;
    }

    public int getInitialHealthAmount() {
        return initialHealth;
    }

    protected int getCharacteristicValue(Characteristic type){
        return characteristics.get(type);
    }

    protected void setCharacteristicValue(Characteristic type, int value){
        characteristics.put(type, value);
    }

    public List<PassiveSkill> getPassiveSkills() {
        return passiveSkills;
    }

    public Subject<Integer> getHealthObservable() {
        return healthObservable;
    }

    public String getName() {
        return name;
    }

}
