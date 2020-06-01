package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


public class BattleParticipant {

    protected List<Skill> passiveSkills;

    private final BehaviorSubject<Integer> healthObservable;


    private EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);
    {
        for(Characteristic c : Characteristic.values()) {
            characteristics.put(c,0);
        }
    }

    public BattleParticipant(int healthPoints, int manaPoints) {
        this.characteristics.put(Characteristic.HEALTH, healthPoints);
        this.characteristics.put(Characteristic.MANA, manaPoints);
        this.characteristics.put(Characteristic.COOLDOWN, 100);
        this.characteristics.put(Characteristic.CAST_TIME,100);
        this.healthObservable = BehaviorSubject.create();
        this.healthObservable.onNext(healthPoints);
    }

    public BehaviorSubject<Integer> getHealthObservable() {
        return healthObservable;
    }

    public int getCharacteristicValue(Characteristic type){
        return characteristics.get(type);
    }

    public void applySkillCharacteristic(SkillCharacteristic sc) {
        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue());
        characteristics.put(c, changedValue);
        if (c == Characteristic.HEALTH) {
            healthObservable.onNext(changedValue);
        }
    }

    public void setPassiveSkills(List<Skill> passiveSkills) {
        this.passiveSkills = passiveSkills;
    }

    public List<Skill> getPassiveSkills() {
        return passiveSkills;
    }
}
