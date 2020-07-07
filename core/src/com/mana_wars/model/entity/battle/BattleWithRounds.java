package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.enemy.EnemyFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class BattleWithRounds implements BattleConfig {

    private BaseBattle currentRoundBattle;

    private final BattleParticipant user;
    private final List<BattleParticipant> userSide;
    private final EnemyFactory enemyFactory;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final Subject<BattleSummaryData> finishBattleObservable;

    private final List<BattleSummaryData> battleSummaryDataList = new ArrayList<>();

    private final BattleRoundsObserver observer;

    private final int roundsCount;
    private int currentRound;

    BattleWithRounds(BattleParticipant user, List<BattleParticipant> userSide, EnemyFactory enemyFactory,
                     int roundsCount, BattleRoundsObserver observer) {
        this.user = user;
        this.userSide = userSide;
        this.enemyFactory = enemyFactory;
        this.roundsCount = roundsCount;

        this.observer = observer;

        this.currentRound = 0;

        this.finishBattleObservable = PublishSubject.create();
    }


    @Override
    public void init() {
        initRound();
    }

    private void initRound(){
        this.currentRoundBattle = new BaseBattle(user, userSide, enemyFactory.generateEnemies());
        currentRoundBattle.init();
        disposable.add(currentRoundBattle.getFinishBattleObservable().subscribe(battleSummaryData -> {

            battleSummaryDataList.add(battleSummaryData);

            if(user.isAlive() && currentRound < roundsCount){
                changeRound();
            }
            else {
                BattleSummaryData fbsd = new BattleSummaryData();

                for (BattleSummaryData bsd : battleSummaryDataList){
                    //fbsd.dosmth(bsd)
                    //TODO combine BattleSummaryData
                }
                finishBattleObservable.onNext(fbsd);
            }

        }, Throwable::printStackTrace));
    }

    private synchronized void changeRound(){
        this.currentRound++;

        observer.setCurrentRound(currentRound);

        final double battleTime = this.currentRoundBattle.getBattleTime();
        initRound();
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
    public Subject<BattleSummaryData> getFinishBattleObservable() {
        return finishBattleObservable;
    }

    @Override
    public void dispose() {
        this.disposable.dispose();
    }
}
