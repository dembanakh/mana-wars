package com.mana_wars.model.entity.battle.builder;

import com.mana_wars.model.entity.battle.Battle;
import com.mana_wars.model.entity.battle.BattleStateObserver;
import com.mana_wars.model.entity.battle.BattleWithRounds;
import com.mana_wars.model.entity.enemy.DungeonEnemyFactory;
import com.mana_wars.model.entity.user.UserDungeonsAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

import static io.reactivex.Observable.combineLatest;

public class DungeonBattleBuilder implements BattleBuilder {

    private final UserDungeonsAPI user;
    private final DungeonEnemyFactory dungeonEnemyFactory;

    public DungeonBattleBuilder(UserDungeonsAPI user, DungeonEnemyFactory dungeonEnemyFactory) {
        this.user = user;
        this.dungeonEnemyFactory = dungeonEnemyFactory;
    }

    @Override
    public Battle build(BattleStateObserver observer) {
        //return new BaseBattle(user.prepareBattleParticipant(), new ArrayList<>(), dungeonEnemyFactory.generateEnemies());
        return new BattleWithRounds(user.prepareBattleParticipant(), new ArrayList<>(),  dungeonEnemyFactory,
                dungeonEnemyFactory.getDungeon().getRounds(), observer).init();
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
