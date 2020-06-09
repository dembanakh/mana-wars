package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleConfig;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.Subject;

public class BattleInteractor {

    private final UserBattleAPI user;

    private final DatabaseRepository databaseRepository;

    private BattleConfig battle;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public BattleInteractor(final UserBattleAPI user, final DatabaseRepository databaseRepository) {
        this.user = user;
        this.databaseRepository = databaseRepository;
    }

    public void init(BattleInitializationObserver observer, BattleConfig battle) {
        this.battle = battle;
        this.user.reInitCharacteristics();
        disposable.add(databaseRepository.getUserSkills().subscribe(skills -> {
            battle.getUser().initSkills(skills.activeSkills, skills.passiveSkills);
            //TODO fetch and init UserSide and EnemySide skills

            observer.setSkills(skills.activeSkills, skills.passiveSkills);
            battle.init();
            observer.setOpponents(battle.getUser(), battle.getUserSide(), battle.getEnemySide());
            battle.start();
            observer.onStartBattle();
        }, Throwable::printStackTrace));
    }

    public void updateBattle(float timeDelta){
        battle.update(timeDelta);
    }

    public boolean tryFinishBattle() {
        if (battle.checkFinish()) {
            battle.finish();
            return true;
        } else return false;
    }

    public boolean tryApplyUserSkill(int skillIndex) {
        return user.tryApplyActiveSkill(skillIndex);
    }

    public Subject<Integer> getUserHealthObservable() {
        return battle.getUser().getHealthObservable();
    }

    public Subject<Integer> getEnemyHealthObservable(int index) {
        return battle.getEnemySide().get(index).getHealthObservable();
    }

    public BattleParticipant getUser() {
        return battle.getUser();
    }

    public void dispose() {
        disposable.dispose();
    }

}
