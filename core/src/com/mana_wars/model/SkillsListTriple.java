package com.mana_wars.model;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public final class SkillsListTriple {

    public final List<Skill> allSkills;
    public final List<ActiveSkill> activeSkills;
    public final List<PassiveSkill> passiveSkills;

    public SkillsListTriple() {
        allSkills = new ArrayList<>();
        activeSkills = new ArrayList<>();
        passiveSkills = new ArrayList<>();
    }

    public SkillsListTriple(List<Skill> allSkills, List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills) {
        this.allSkills = allSkills;
        this.activeSkills = activeSkills;
        this.passiveSkills = passiveSkills;
    }
}
