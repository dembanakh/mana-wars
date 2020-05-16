package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;

import java.util.ArrayList;
import java.util.List;

public class SkillsInteractor {

    public List<Skill> getSkillsList() {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            skills.add(SkillFactory.getNewSkill());
        }
        return skills;
    }

}
