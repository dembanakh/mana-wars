package com.mana_wars.model.repository;

import com.mana_wars.model.SkillsListTriple;
import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.entity.enemy.Mob;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DatabaseRepository {

    Single<List<Skill>> getSkillsList();
    Single<SkillsListTriple> getUserSkills();
    Completable insertUserSkill(Skill s);

    Completable mergeSkills(Skill toUpdate, Skill toDelete);
    Completable moveSkill(Skill toUpdate, int index);
    Completable swapSkills(Skill skillSource, Skill skillTarget);

    Single<List<Dungeon>> getDungeons();
    Single<List<Mob>> getMobsListByDungeon(Dungeon dungeon);
}
