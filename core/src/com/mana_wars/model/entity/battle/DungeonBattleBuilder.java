package com.mana_wars.model.entity.battle;

import com.mana_wars.model.entity.enemy.DungeonEnemyFactory;
import com.mana_wars.model.entity.user.UserDungeonsAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

import static io.reactivex.Observable.combineLatest;

public class DungeonBattleBuilder extends BattleBuilder {

    private final UserDungeonsAPI user;
    private final DungeonEnemyFactory dungeonEnemyFactory;
    private BattleRoundsObserver observer;


    public DungeonBattleBuilder(UserDungeonsAPI user, DungeonEnemyFactory dungeonEnemyFactory) {
        this.user = user;
        this.dungeonEnemyFactory = dungeonEnemyFactory;
    }

    public void setObserver(BattleRoundsObserver observer) {
        this.observer = observer;
    }

    @Override
    public BattleConfig build() {
        //return new BaseBattle(user.prepareBattleParticipant(), new ArrayList<>(), dungeonEnemyFactory.generateEnemies());
        return new BattleWithRounds(user.prepareBattleParticipant(), new ArrayList<>(),  dungeonEnemyFactory, 2, observer);
    }

    @Override
    public void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback) {
        disposable.add(
                combineLatest(databaseRepository.getUserSkills().toObservable(),
                        databaseRepository.getMobsListByDungeon(dungeonEnemyFactory.getDungeon()).toObservable(),
                        (skills, mobs) -> {
                            user.initSkills(skills.activeSkills, skills.passiveSkills);
                            dungeonEnemyFactory.setMobs(mobs);
                            return true;
                        }
                ).subscribe((x) -> {
                    callback.run();
                }, Throwable::printStackTrace)
        );
    }
}
