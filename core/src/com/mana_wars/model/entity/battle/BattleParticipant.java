package com.mana_wars.model.entity.battle;

import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;

import java.util.EnumMap;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BattleParticipant {

    private final String name;
    protected final int initialHealth;

    //TODO thing about delete this
    protected Battle battle;

    protected final BattleSkill[] battleSkills = new BattleSkill[GameConstants.USER_ACTIVE_SKILL_COUNT];

    protected List<PassiveSkill> passiveSkills;
    private final Subject<Integer> healthObservable;

    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);
    {
        for(Characteristic c : Characteristic.values()) {
            characteristics.put(c,0);
        }
    }

    public BattleParticipant(String name, int healthPoints) {
        this.name = name;
        this.initialHealth = healthPoints;
        this.characteristics.put(Characteristic.HEALTH, healthPoints);
        this.characteristics.put(Characteristic.MANA, 0);
        this.characteristics.put(Characteristic.COOLDOWN, 100);
        this.characteristics.put(Characteristic.CAST_TIME,100);
        healthObservable = BehaviorSubject.createDefault(healthPoints);
    }

    public abstract void start();
    public abstract void update(final double currentTime);

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void applySkillCharacteristic(SkillCharacteristic sc) {
        Characteristic c = sc.getCharacteristic();
        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue());
        //ToDO think about changing code place
        changedValue = Math.min(changedValue, initialHealth);
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

    public void initSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills){
        this.passiveSkills = passiveSkills;
        for (int i=0; i<GameConstants.USER_ACTIVE_SKILL_COUNT; i++){
            this.battleSkills[i] = new BattleSkill(activeSkills.get(i));
        }
    }

}
