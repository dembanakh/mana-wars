package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.skills.Skill;

class EmptySkillOperationQuery implements OperationQuery<Skill, Boolean> {
    EmptySkillOperationQuery() {}

    public OperationQuery<Skill, Boolean> from(Skill source) {
        return this;
    }

    public OperationQuery<Skill, Boolean> to(Skill target) {
        return this;
    }

    public Boolean validate() {
        return false;
    }
}
