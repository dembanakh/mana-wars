package com.mana_wars.model.repository;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.skills.Skill;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DatabaseRepository {

    Single<List<Skill>> getSkillsList();
    Single<Map<SkillTable,List<Skill>>> getUserSkills();
    Completable insertUserSkill(Skill s);

    Completable mergeSkills(Skill toUpdate, Skill toDelete);
    Completable moveSkill(Skill toUpdate, int index);
    Completable swapSkills(Skill skillSource, Skill skillTarget);
}
