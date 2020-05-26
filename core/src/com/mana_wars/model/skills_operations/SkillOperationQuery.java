package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.skills.Skill;

class SkillOperationQuery implements OperationQuery<Skill, Boolean> {
    final TableOperationQuery tableQuery;
    Skill source;
    Skill target;

    SkillOperationQuery(TableOperationQuery tableQuery) {
        this.tableQuery = tableQuery;
    }

    public OperationQuery<Skill, Boolean> from(Skill source) {
        this.source = source;
        return this;
    }

    public OperationQuery<Skill, Boolean> to(Skill target) {
        if (this.source == null) throw new IllegalStateException("SkillOperationQuery has no source");
        this.target = target;
        return this;
    }

    public Boolean validate() {
        if (this.source == null || this.target == null)
            throw new IllegalStateException("SkillOperationQuery is not ready for validation");
        return tableQuery.operation.validate(this);
    }
}
