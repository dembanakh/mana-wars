package com.mana_wars.model.interactor;

import com.mana_wars.model.skills_operations.SkillsOperations;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public class SkillsInteractor {

    private DatabaseRepository databaseRepository;

    public SkillsInteractor(DatabaseRepository databaseRepository){
        this.databaseRepository = databaseRepository;
    }

    public Single<Map<SkillsOperations.Table,List<Skill>>> getUserSkills() {
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

    public boolean validateAnyOperation(SkillsOperations.Table tableSource, SkillsOperations.Table tableTarget,
                                        Skill skillSource, Skill skillTarget) {
        for (SkillsOperations operation : SkillsOperations.values()) {
            if (validateOperation(operation, tableSource, tableTarget, skillSource, skillTarget))
                return true;
        }
        return false;
    }

    public boolean validateOperation(SkillsOperations operation, SkillsOperations.Table tableSource, SkillsOperations.Table tableTarget,
                                     Skill skillSource, Skill skillTarget) {
        return SkillsOperations.can(operation)
                                .from(tableSource)
                                .to(tableTarget)
                                .validate()
                                .from(skillSource)
                                .to(skillTarget)
                                .validate();
    }

}
