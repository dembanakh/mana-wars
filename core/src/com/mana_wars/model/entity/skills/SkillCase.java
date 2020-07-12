package com.mana_wars.model.entity.skills;

public class SkillCase {

    public Skill open(/*WHERE SHOULD THIS BE?*/ Iterable<Skill> skills) {
        return SkillFactory.getNewSkill(skills);
    }

}
