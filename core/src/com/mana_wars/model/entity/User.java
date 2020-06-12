package com.mana_wars.model.entity;

import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.BattleSkill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.entity.user.UserGreetingAPI;
import com.mana_wars.model.entity.user.UserMenuAPI;
import com.mana_wars.model.entity.user.UserSkillsAPI;
import com.mana_wars.model.repository.UserLevelRepository;
import com.mana_wars.model.repository.UserManaRepository;
import com.mana_wars.model.repository.UsernameRepository;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class User extends BattleParticipant implements UserMenuAPI, UserSkillsAPI, UserBattleAPI, UserGreetingAPI {

    private final Subject<Integer> manaAmountObservable;
    private final Subject<Integer> userLevelObservable;

    private final UserManaRepository userManaRepository;
    private final UserLevelRepository userLevelRepository;

    private BattleSkill toApply;
    private double battleTime;

    public User(UserManaRepository userManaRepository, UserLevelRepository userLevelRepository,
                UsernameRepository usernameRepository) {
        super(1000);
        this.userManaRepository = userManaRepository;
        this.userLevelRepository = userLevelRepository;
        manaAmountObservable = BehaviorSubject.createDefault(initManaAmount());
        userLevelObservable = BehaviorSubject.createDefault(initUserLevel());
        if (usernameRepository.hasUsername()) setName(usernameRepository.getUsername());
    }

    private int initManaAmount() {
        final int userMana = userManaRepository.getUserMana();
        setCharacteristicValue(Characteristic.MANA, userMana);
        return userMana;
    }

    private int initUserLevel() {
        return userLevelRepository.getUserLevel();
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double currentTime) {
        synchronized (this){
            battleTime = currentTime;

            if (toApply != null && toApply.isAvailableAt(currentTime)){
                super.applySkill(toApply.skill, currentTime);
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


    @Override
    public Subject<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    @Override
    public Subject<Integer> getUserLevelObservable() {
        return userLevelObservable;
    }

    private void reInitCharacteristics() {
        setCharacteristicValue(Characteristic.HEALTH, initialHealth);
    }

    @Override
    public BattleParticipant prepareBattleParticipant() {
        reInitCharacteristics();
        return this;
    }

    @Override
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

    @Override
    public void updateManaAmount(int delta) {
        setManaAmount(userManaRepository.getUserMana() + delta);
    }

    private void setManaAmount(int userMana){
        setCharacteristicValue(Characteristic.MANA, userMana);
        userManaRepository.setUserMana(userMana);
        manaAmountObservable.onNext(userMana);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

}
