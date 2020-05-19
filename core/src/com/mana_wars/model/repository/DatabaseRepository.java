package com.mana_wars.model.repository;

import com.mana_wars.model.entity.skills.Skill;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DatabaseRepository {

    Single<List<Skill>> getSkillsList();
    Single<List<Skill>> getUserSkills();
    Completable insertUserSkill(Skill s);
    Completable mergeSkills(Skill toUpdate, Skill toDelete);
}
