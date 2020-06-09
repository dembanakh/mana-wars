package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BattleParticipant {

    private final String name;

    protected Battle battle;

    protected List<PassiveSkill> passiveSkills;

    private final Subject<Integer> healthObservable;

    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);
    {
        for(Characteristic c : Characteristic.values()) {
            characteristics.put(c,0);
        }
    }

    public BattleParticipant(String name, int healthPoints, int manaPoints) {
        this.name = name;
        this.characteristics.put(Characteristic.HEALTH, healthPoints);
        this.characteristics.put(Characteristic.MANA, manaPoints);
        this.characteristics.put(Characteristic.COOLDOWN, 100);
        this.characteristics.put(Characteristic.CAST_TIME,100);
        healthObservable = BehaviorSubject.createDefault(healthPoints);
    }

    public abstract void start();
    public abstract void act(float delta);

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void applySkillCharacteristic(SkillCharacteristic sc) {
        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue());
        characteristics.put(c, changedValue);
        if (c == Characteristic.HEALTH)
            healthObservable.onNext(changedValue);
    }

    public boolean isAlive() {
        return getCharacteristicValue(Characteristic.HEALTH) > 0;
    }

    public int getCharacteristicValue(Characteristic type){
        return characteristics.get(type);
    }

    public void setPassiveSkills(List<PassiveSkill> passiveSkills) {
        this.passiveSkills = passiveSkills;
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
