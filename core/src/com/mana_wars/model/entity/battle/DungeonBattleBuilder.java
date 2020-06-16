package com.mana_wars.model.entity.battle;

import com.mana_wars.model.SkillsListTriple;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.enemy.DungeonEnemyFactory;
import com.mana_wars.model.entity.enemy.Mob;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;

import static io.reactivex.Observable.combineLatest;

public class DungeonBattleBuilder extends BaseBattleBuilder{

    private final User user;
    private final DungeonEnemyFactory dungeonEnemyFactory;

    public DungeonBattleBuilder(User user, DungeonEnemyFactory dungeonEnemyFactory){
        this.user = user;
        this.dungeonEnemyFactory = dungeonEnemyFactory;
    }

    @Override
    public BattleConfig build() {
        return new BaseBattle(user.prepareBattleParticipant(), new ArrayList<>(), dungeonEnemyFactory.generateEnemies());
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
                ).subscribe((x)->{
                        callback.run();
                    }, Throwable::printStackTrace)
        );
    }
}
