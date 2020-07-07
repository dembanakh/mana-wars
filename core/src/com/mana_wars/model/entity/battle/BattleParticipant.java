package com.mana_wars.model.entity.battle;

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

    private static Random random = new Random();

    private Battle battle;
    private int currentTarget = -1;

    private final String name;
    private final int initialHealth;

    protected final List<BattleSkill> battleSkills = new ArrayList<>();
    protected final Iterable<PassiveSkill> passiveSkills;

    private final EnumMap<Characteristic, Integer> characteristics = new EnumMap<>(Characteristic.class);

    private final Subject<Integer> healthObservable;

    public BattleParticipant(String name, int initialHealth, Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills) {
        this(name, initialHealth, new ArrayList<BattleSkill>(), passiveSkills);

        for (ActiveSkill s : activeSkills) {
            battleSkills.add(new BattleSkill(s));
        }
    }

    protected BattleParticipant(String name, int initialHealth, List<BattleSkill> battleSkills, Iterable<PassiveSkill> passiveSkills) {
        this.name = name;
        this.initialHealth = initialHealth;
        setCharacteristicValue(Characteristic.HEALTH, initialHealth);
        setCharacteristicValue(Characteristic.MANA, 0);
        setCharacteristicValue(Characteristic.CAST_TIME, 100);
        setCharacteristicValue(Characteristic.COOLDOWN, 100);
        //game private data
        setCharacteristicValue(Characteristic._MANA_COST, 100);

        healthObservable = BehaviorSubject.create();
        this.passiveSkills = passiveSkills;
        for (BattleSkill skill : battleSkills) {
            this.battleSkills.add(new BattleSkill(skill.skill));
        }
    }

    public abstract void update(final double currentTime);

    void start() {
        healthObservable.onNext(initialHealth);
        changeTarget();
    }

    public int changeTarget(){
        List<Integer> aliveOpponents = this.aliveOpponents();

        if (aliveOpponents.isEmpty())
            return currentTarget;

        currentTarget = aliveOpponents.get(random.nextInt(aliveOpponents.size()));
        return currentTarget;
    }

    public void applySkillCharacteristic(SkillCharacteristic sc, int skillLevel) {
        Characteristic c = sc.getCharacteristic();


        int changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(), sc.getValue(skillLevel));

        //TODO very strongly think about it!
        if(sc.isManaCost()){
            changedValue = c.changeValue(characteristics.get(c), sc.getChangeType(),
                    sc.getValue(skillLevel) * getCharacteristicValue(Characteristic._MANA_COST) / 100);
        }

        if (c == Characteristic.HEALTH) {
            changedValue = Math.min(changedValue, initialHealth);
            healthObservable.onNext(changedValue);
        }
        setCharacteristicValue(c, changedValue);
    }

    protected synchronized void applySkill(ActiveSkill skill, double currentTime) {
        double castTime = skill.getCastTime(getCharacteristicValue(Characteristic.CAST_TIME));
        double cooldown = skill.getCooldown(getCharacteristicValue(Characteristic.COOLDOWN));
        battle.requestSkillApplication(this, skill, castTime);
        for (BattleSkill battleSkill : battleSkills) {
            battleSkill.updateAvailabilityTime(currentTime + castTime +
                    (battleSkill.skill == skill ? cooldown : 0));
        }
    }

    private List<Integer> aliveOpponents() {
        List<BattleParticipant> opponents = battle.getOpponents(this);
        List<Integer> aliveOpponents = new ArrayList<>();
        for (int i = 0, n = opponents.size(); i < n; i++) {
            if (i != currentTarget && opponents.get(i).isAlive()){
                aliveOpponents.add(i);
            }
        }
        return aliveOpponents;
    }


    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public boolean isAlive() {
        return getCharacteristicValue(Characteristic.HEALTH) > 0;
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

    public int getCharacteristicValue(Characteristic type) {
        return characteristics.get(type);
    }

    protected void setCharacteristicValue(Characteristic type, int value) {
        characteristics.put(type, value);
    }

}
