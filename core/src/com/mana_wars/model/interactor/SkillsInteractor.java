package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;

import io.reactivex.Single;

public class SkillsInteractor {

    private DatabaseRepository databaseRepository;

    public SkillsInteractor(DatabaseRepository databaseRepository){
        this.databaseRepository = databaseRepository;
    }

    public Single<List<Skill>> getUserSkills() {
        return databaseRepository.getUserSkills();
    }

}
