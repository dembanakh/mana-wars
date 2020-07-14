package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.base.BaseBattle;
import com.mana_wars.model.entity.battle.base.Battle;
import com.mana_wars.model.entity.battle.base.BattleStartMode;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
import com.mana_wars.model.entity.battle.participant.SkillCharacteristicApplicationMode;
import com.mana_wars.model.entity.enemy.EnemyFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.SingleSubject;

public class BattleWithRounds implements Battle {

    private BaseBattle currentRoundBattle;

    private final BattleParticipant user;
    private final List<BattleParticipant> userSide;
    private final EnemyFactory enemyFactory;

    private final SingleSubject<BattleSummaryData> finishBattleObservable;

    private final List<BattleSummaryData> battleSummaryDataList = new ArrayList<>();

    private final BattleStateObserver observer;

    private final int roundsCount;
    private int currentRound;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public BattleWithRounds(BattleParticipant user, List<BattleParticipant> userSide, EnemyFactory enemyFactory,
                            int roundsCount, BattleStateObserver observer) {
        this.user = user;
        this.userSide = userSide;
        this.enemyFactory = enemyFactory;
        this.roundsCount = roundsCount;
        this.observer = observer;

        this.currentRound = 0;
        this.finishBattleObservable = SingleSubject.create();
    }

    public Battle init() {
        currentRoundBattle = new BaseBattle(user, userSide, enemyFactory.generateEnemies()).init();
        subscribeOnBattleFinish();
        return this;
    }

    @Override
    public void start() {
        observer.setCurrentRound(currentRound);
        currentRoundBattle.start();
    }

    @Override
    public synchronized void update(float timeDelta) {
        currentRoundBattle.update(timeDelta);
    }

    private synchronized void changeRound() {
        currentRound++;
        observer.setCurrentRound(currentRound);

        //TODO
        user.setCharacteristicValue(Characteristic.CAST_TIME, 100);
        user.setCharacteristicValue(Characteristic.COOLDOWN, 100);

        user.setCharacteristicApplicationMode(SkillCharacteristicApplicationMode.NO_MANA_CONSUMPTION);

        currentRoundBattle = new BaseBattle(user, userSide, enemyFactory.generateEnemies(),
                BattleStartMode.ROUND, currentRoundBattle.getBattleTime()).init();
        subscribeOnBattleFinish();

        user.setCharacteristicApplicationMode(SkillCharacteristicApplicationMode.DEFAULT);

        observer.updateDurationCoefficients(
                user.getCharacteristicValue(Characteristic.CAST_TIME),
                user.getCharacteristicValue(Characteristic.COOLDOWN));

        currentRoundBattle.start();
        observer.setEnemies(currentRoundBattle.getEnemySide(), user.getCurrentTarget());
    }

    private void subscribeOnBattleFinish() {
        disposable.add(currentRoundBattle.getFinishBattleObservable().subscribe(battleSummaryData -> {
            battleSummaryDataList.add(battleSummaryData);

            if (user.isAlive() && currentRound < roundsCount) {
                changeRound();
            }
            else {
                BattleSummaryData finalSummaryData = new BattleSummaryData();
                for (BattleSummaryData bsd : battleSummaryDataList){
                    finalSummaryData.combineWith(bsd);
                }
                finishBattleObservable.onSuccess(finalSummaryData);
            }
        }, Throwable::printStackTrace));
    }

    //region Getters
    @Override
    public BattleParticipant getUser() {
        return user;
    }

    @Override
    public List<BattleParticipant> getUserSide() {
        return userSide;
    }

    @Override
    public List<BattleParticipant> getEnemySide() {
        return currentRoundBattle.getEnemySide();
    }

    @Override
    public Single<BattleSummaryData> getFinishBattleObservable() {
        return finishBattleObservable;
    }
    //endregion

    @Override
    public void dispose() {
        disposable.dispose();
    }
}
