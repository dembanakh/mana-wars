package com.mana_wars.model.entity.battle.builder;

import com.mana_wars.model.entity.battle.base.Battle;
import com.mana_wars.model.entity.battle.BattleStateObserver;
import com.mana_wars.model.entity.battle.BattleWithRounds;
import com.mana_wars.model.entity.enemy.DungeonEnemyFactory;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.user.UserBattleAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static io.reactivex.Observable.combineLatest;

public class DungeonBattleBuilder implements BattleBuilder {

    private UserBattleAPI user;
    private final DungeonEnemyFactory dungeonEnemyFactory;

    private List<ActiveSkill> userActiveSkills;
    private Iterable<PassiveSkill> userPassiveSkills;

    public DungeonBattleBuilder(DungeonEnemyFactory dungeonEnemyFactory) {
        this.dungeonEnemyFactory = dungeonEnemyFactory;
    }

    @Override
    public void setUser(UserBattleAPI user) {
        this.user = user;
    }

    @Override
    public Battle build(BattleStateObserver observer) {
        //return new BaseBattle(user.prepareBattleParticipant(), new ArrayList<>(), dungeonEnemyFactory.generateEnemies());
        return new BattleWithRounds(user.prepareBattleParticipant(userActiveSkills, userPassiveSkills),
                new ArrayList<>(), dungeonEnemyFactory,
                dungeonEnemyFactory.getDungeon().getRounds(), observer).init();
    }

    @Override
    public void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback) {
        disposable.add(
                combineLatest(databaseRepository.getUserSkills().toObservable(),
                        databaseRepository.getMobsListByDungeon(dungeonEnemyFactory.getDungeon()).toObservable(),
                        (skills, mobs) -> {
                            userActiveSkills = skills.activeSkills;
                            userPassiveSkills = skills.passiveSkills;
                            dungeonEnemyFactory.setMobs(mobs);
                            return true;
                        }
                ).subscribe((x) -> callback.run(), Throwable::printStackTrace)
        );
    }
}
