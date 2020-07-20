package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.battle.BattleStateObserverStarter;
import com.mana_wars.model.entity.battle.builder.BattleBuilder;
import com.mana_wars.model.entity.battle.base.Battle;
import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.data.ReadableBattleSummaryData;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class BattleInteractor extends BaseInteractor<UserBattleAPI> {

    private Battle battle;
    private final DatabaseRepository databaseRepository;

    public BattleInteractor(final UserBattleAPI user, final DatabaseRepository databaseRepository) {
        super(user);
        this.databaseRepository = databaseRepository;
    }

    public void init(final BattleStateObserverStarter observer, final BattleBuilder battleBuilder) {
        battleBuilder.setUser(user);
        battleBuilder.fetchData(disposable, databaseRepository, () -> {
            this.battle = battleBuilder.build(observer);

            observer.setSkills(user.getActiveSkills());
            observer.updateDurationCoefficients(
                    battle.getUser().getCharacteristicValue(Characteristic.CAST_TIME),
                    battle.getUser().getCharacteristicValue(Characteristic.COOLDOWN));
            battle.start();
            observer.setOpponents(battle.getUser(), battle.getUserSide(), battle.getEnemySide());
            observer.onStartBattle();
        });
    }

    public void updateBattle(float timeDelta) {
        battle.update(timeDelta);
    }

    public boolean tryApplyUserSkill(ActiveSkill skill) {
        return user.tryApplyActiveSkill(skill);
    }

    public Observable<Integer> getUserManaAmountObservable() {
        return user.getManaAmountObservable();
    }

    public Single<ReadableBattleSummaryData> getFinishBattleObservable() {
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
