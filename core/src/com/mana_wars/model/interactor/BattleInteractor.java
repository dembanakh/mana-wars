package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.builder.BattleBuilder;
import com.mana_wars.model.entity.battle.BattleConfig;
import com.mana_wars.model.entity.battle.BattleStateObserver;
import com.mana_wars.model.entity.battle.BattleSummaryData;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.subjects.Subject;

public final class BattleInteractor extends BaseInteractor {

    private final UserBattleAPI user;
    private BattleConfig battle;
    private final DatabaseRepository databaseRepository;

    public BattleInteractor(final UserBattleAPI user, final DatabaseRepository databaseRepository) {
        this.user = user;
        this.databaseRepository = databaseRepository;
    }

    public void init(final BattleStateObserver observer, final BattleBuilder battleBuilder) {
        battleBuilder.fetchData(disposable, databaseRepository, () -> {
            this.battle = battleBuilder.build(observer);

            battle.init();
            observer.setSkills(user.getActiveSkills());
            observer.updateDurationCoefficients(
                    battle.getUser().getCharacteristicValue(Characteristic.CAST_TIME),
                    battle.getUser().getCharacteristicValue(Characteristic.COOLDOWN));
            observer.setOpponents(battle.getUser(), battle.getUserSide(), battle.getEnemySide());
            battle.start();
            observer.onStartBattle();
        });
    }

    public void updateBattle(float timeDelta) {
        battle.update(timeDelta);
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

    public Subject<BattleSummaryData> getFinishBattleObservable() {
        return battle.getFinishBattleObservable();
    }

    public int changeUserTarget() {
        return battle.getUser().changeTarget();
    }


    public int getEnemiesNumber() {
        return battle.getEnemySide().size();
    }

    @Override
    public void dispose(){
        super.dispose();
        this.battle.dispose();
    }
}
