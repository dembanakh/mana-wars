package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleConfig;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class BattleInteractor {

    private LocalUserDataRepository localUserDataRepository;
    private DatabaseRepository databaseRepository;

    private final Subject<Integer> userHealthObservable;
    private final Subject<Integer> enemyHealthObservable;

    private BattleConfig battle;

    private CompositeDisposable disposable = new CompositeDisposable();

    public BattleInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
        this.userHealthObservable = BehaviorSubject.createDefault(100);
        this.enemyHealthObservable = BehaviorSubject.createDefault(100);
    }

    public void init(BattlePresenterCallback callback, BattleConfig battle) {
        this.battle = battle;
        disposable.add(databaseRepository.getUserSkills().subscribe(skills -> {

            battle.getUser().initSkills(skills.activeSkills, skills.passiveSkills);
            //TODO fetch and init UserSide and EnemySide skills

            callback.setSkills(skills.activeSkills, skills.passiveSkills);
            battle.init();
            callback.setOpponents(battle.getUser(), battle.getUserSide(), battle.getEnemySide());
            battle.start();
            callback.startBattle();
        }, Throwable::printStackTrace));
    }

    public Subject<Integer> getUserHealthObservable() {
        return battle.getUser().getHealthObservable();
    }

    public Subject<Integer> getEnemyHealthObservable(int index) {
        return battle.getEnemySide().get(index).getHealthObservable();
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
        return battle.getUser().tryApplyActiveSkill(skillIndex);
    }

    public void dispose() {
        disposable.dispose();
    }

}
