package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.base.ValueChangeType;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillCharacteristic;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.ArrayList;
import java.util.Arrays;
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
