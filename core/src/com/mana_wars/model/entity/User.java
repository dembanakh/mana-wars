package com.mana_wars.model.entity;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;
import com.mana_wars.model.repository.UserManaRepository;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class User extends BattleParticipant {

    private final Subject<Integer> manaObservable;
    private final UserManaRepository userManaRepository;

    public User(UserManaRepository userManaRepository) {
        super("User", 1000);
        this.userManaRepository = userManaRepository;
        final int userMana = userManaRepository.getUserMana();
        manaObservable = BehaviorSubject.createDefault(userMana);
        setCharacteristicValue(Characteristic.MANA, userMana);
    }

    public Subject<Integer> getManaObservable() {
        return manaObservable;
    }





    @Override
    public void start() {

    }

    private BattleSkill toApply;
    private double battleTime;

    @Override
    public void update(double currentTime) {

        synchronized (this){
            battleTime = currentTime;

            //todo refactor
            if (toApply != null && toApply.isAvailableAt(currentTime)){
                battle.requestSkillApplication(this, toApply.skill, toApply.skill.getCastTime()*getCharacteristicValue(Characteristic.CAST_TIME)/100);
                for (BattleSkill battleSkill : battleSkills){
                    if (battleSkill == toApply){
                        toApply.updateAvailabilityTime(currentTime
                                + toApply.skill.getCastTime()*getCharacteristicValue(Characteristic.CAST_TIME)/100
                                + toApply.skill.getCooldown()*getCharacteristicValue(Characteristic.COOLDOWN)/100);
                    }
                    else battleSkill.updateAvailabilityTime(
                            currentTime+toApply.skill.getCastTime());
                }
            }
            toApply = null;
        }

    }

    @Override
    public void applySkillCharacteristic(SkillCharacteristic sc) {
        super.applySkillCharacteristic(sc);

        Characteristic c = sc.getCharacteristic();
        if (c == Characteristic.MANA){
            setManaAmount(getCharacteristicValue(c));
        }

    }

    public User reInitCharacteristics() {
        setCharacteristicValue(Characteristic.HEALTH, initialHealth);
        return this;
    }

    public boolean tryApplyActiveSkill(int skillIndex) {

        BattleSkill targetBattleSkill = battleSkills[skillIndex];

        if (getCharacteristicValue(Characteristic.MANA) >= targetBattleSkill.skill.getManaCost()
            && targetBattleSkill.isAvailableAt(battleTime)) {

            synchronized (this){
                toApply = targetBattleSkill;
            }
            return true;
        }
        return false;
    }

    public void updateManaAmount(int delta) {
        setManaAmount(userManaRepository.getUserMana() + delta);
    }

    private void setManaAmount(int userMana){
        setCharacteristicValue(Characteristic.MANA, userMana);
        userManaRepository.setUserMana(userMana);
        manaObservable.onNext(userMana);
    }


}
