package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserBaseAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;

import io.reactivex.Single;

public final class SkillsInfoInteractor extends BaseInteractor<UserBaseAPI> {

    private final DatabaseRepository databaseRepository;

    public SkillsInfoInteractor(final DatabaseRepository databaseRepository) {
        super(null);
        this.databaseRepository = databaseRepository;
    }

    public Single<List<Skill>> getAllSkills() {
        return databaseRepository.getSkillsList();
    }

}
