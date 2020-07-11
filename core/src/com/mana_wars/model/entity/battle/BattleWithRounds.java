package com.mana_wars.model.entity.battle;


import com.mana_wars.model.entity.base.Characteristic;
import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.entity.battle.participant.BattleParticipant;
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
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final SingleSubject<com.mana_wars.model.entity.battle.data.BattleSummaryData> finishBattleObservable;

    private final List<com.mana_wars.model.entity.battle.data.BattleSummaryData> battleSummaryDataList = new ArrayList<>();

    private final BattleStateObserver observer;

    private final int roundsCount;
    private int currentRound;

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
        initRound();
        return this;
    }

    private void initRound(){
        this.currentRoundBattle = new BaseBattle(user, userSide, enemyFactory.generateEnemies()).init();

        disposable.add(currentRoundBattle.getFinishBattleObservable().subscribe(battleSummaryData -> {

            battleSummaryDataList.add(battleSummaryData);

            if(user.isAlive() && currentRound < roundsCount){
                changeRound();
            }
            else {
                com.mana_wars.model.entity.battle.data.BattleSummaryData fbsd = new com.mana_wars.model.entity.battle.data.BattleSummaryData();

                for (com.mana_wars.model.entity.battle.data.BattleSummaryData bsd : battleSummaryDataList){
                    fbsd.combineWith(bsd);
                }
                finishBattleObservable.onSuccess(fbsd);
            }

        }, Throwable::printStackTrace));
    }

    private synchronized void changeRound(){
        this.currentRound++;

        this.observer.setCurrentRound(this.currentRound);

        final double battleTime = this.currentRoundBattle.getBattleTime();

        //TODO
        user.setCharacteristicValue(Characteristic.CAST_TIME, 100);
        user.setCharacteristicValue(Characteristic.COOLDOWN, 100);

        user.setCharacteristicValue(Characteristic._MANA_COST,0);
        initRound();
        user.setCharacteristicValue(Characteristic._MANA_COST,100);

        observer.updateDurationCoefficients(
                user.getCharacteristicValue(Characteristic.CAST_TIME),
                user.getCharacteristicValue(Characteristic.COOLDOWN));

        user.changeTarget();
        for (BattleParticipant bp : userSide){
            bp.changeTarget();
        }
        this.currentRoundBattle.start(battleTime);
        observer.setEnemies(user, this.currentRoundBattle.getEnemySide());
    }

    @Override
    public void start() {
        observer.setCurrentRound(currentRound);
        this.currentRoundBattle.start();
    }

    @Override
    public synchronized void update(float timeDelta) {
        this.currentRoundBattle.update(timeDelta);
    }

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

    @Override
    public void dispose() {
        this.disposable.dispose();
    }
}
