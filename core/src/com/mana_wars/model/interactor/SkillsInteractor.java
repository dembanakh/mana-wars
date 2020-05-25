package com.mana_wars.model.interactor;

import com.mana_wars.model.skills_operations.SkillsOperations;
import com.mana_wars.model.entity.SkillTable;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

public class SkillsInteractor {

    private DatabaseRepository databaseRepository;

    private final PublishSubject<Integer> manaAmountObservable;
    private final PublishSubject<Integer> userLevelObservable;

    public SkillsInteractor(DatabaseRepository databaseRepository){
        this.databaseRepository = databaseRepository;
        manaAmountObservable = PublishSubject.create();
        userLevelObservable = PublishSubject.create();
    }

    public Single<Map<SkillTable,List<Skill>>> getUserSkills() {
        return databaseRepository.getUserSkills();
    }

    public Completable mergeSkills(Skill toUpdate, Skill toDelete){
        return databaseRepository.mergeSkills(toUpdate, toDelete);
    }

    public Completable moveSkill(Skill toUpdate, int index){
        return databaseRepository.moveSkill(toUpdate, index);
    }

    public Completable swapSkills(Skill skillSource, Skill skillTarget) {
        return databaseRepository.swapSkills(skillSource, skillTarget);
    }

    public boolean validateAnyOperation(SkillTable tableSource, SkillTable tableTarget,
                                        Skill skillSource, Skill skillTarget) {
        for (SkillsOperations operation : SkillsOperations.values()) {
            if (validateOperation(operation, tableSource, tableTarget, skillSource, skillTarget))
                return true;
        }
        return false;
    }

    public boolean validateOperation(SkillsOperations operation, SkillTable tableSource, SkillTable tableTarget,
                                     Skill skillSource, Skill skillTarget) {
        return SkillsOperations.can(operation)
                                .from(tableSource)
                                .to(tableTarget)
                                .validate()
                                .from(skillSource)
                                .to(skillTarget)
                                .validate();
    }

    public PublishSubject<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    public PublishSubject<Integer> getUserLevelObservable() {
        return userLevelObservable;
    }

}
