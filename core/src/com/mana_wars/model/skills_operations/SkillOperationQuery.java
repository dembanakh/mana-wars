package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.skills.Skill;

class SkillOperationQuery implements OperationQuery<Skill, Boolean> {
    final TableOperationQuery tableQuery;
    Skill source;
    Skill target;

    private boolean sourceSet = false;
    private boolean targetSet = false;

    SkillOperationQuery(TableOperationQuery tableQuery) {
        this.tableQuery = tableQuery;
    }

    public OperationQuery<Skill, Boolean> from(Skill source) {
        if (this.sourceSet) throw new IllegalStateException("SkillOperationQuery cannot have 2 sources");
        this.source = source;
        this.sourceSet = true;
        return this;
    }

    public OperationQuery<Skill, Boolean> to(Skill target) {
        if (!this.sourceSet) throw new IllegalStateException("SkillOperationQuery has no source");
        if (this.targetSet) throw new IllegalStateException("SkillOperationQuery cannot have 2 targets");
        this.target = target;
        this.targetSet = true;
        return this;
    }

    public Boolean validate() {
        if (!this.sourceSet || !this.targetSet)
            throw new IllegalStateException("SkillOperationQuery is not ready for validation");
        return tableQuery.operation.validate(this);
    }

}
