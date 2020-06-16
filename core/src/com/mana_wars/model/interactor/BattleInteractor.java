package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BaseBattleBuilder;
import com.mana_wars.model.entity.battle.BattleConfig;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.subjects.Subject;

public class BattleInteractor extends BaseInteractor{

    private final UserBattleAPI user;
    private BattleConfig battle;
    private final DatabaseRepository databaseRepository;

    public BattleInteractor(final UserBattleAPI user, final DatabaseRepository databaseRepository) {
        this.user = user;
        this.databaseRepository = databaseRepository;
    }

    public void init(final BattleInitializationObserver observer, final BaseBattleBuilder battleBuilder) {
        battleBuilder.fetchData(disposable, databaseRepository, ()-> {
            this.battle = battleBuilder.build();
            battle.init();
            observer.setSkills(user.getActiveSkills());
            observer.setOpponents(battle.getUser(), battle.getUserSide(), battle.getEnemySide());
            battle.start();
            observer.onStartBattle();
        });
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

    public Subject<Integer> getUserManaAmountObservable() {
        return user.getManaAmountObservable();
    }

}
