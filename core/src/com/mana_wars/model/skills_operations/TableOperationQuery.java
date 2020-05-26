package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.skills.Skill;

class TableOperationQuery implements OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> {
    final SkillsOperations operation;
    SkillsOperations.Table source;
    SkillsOperations.Table target;

    TableOperationQuery(SkillsOperations operation) {
        this.operation = operation;
    }

    public OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> from(SkillsOperations.Table source) {
        this.source = source;
        return this;
    }

    public OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> to(SkillsOperations.Table target) {
        if (this.source == null) throw new IllegalStateException("TableOperationQuery has no source");
        this.target = target;
        return this;
    }

    public OperationQuery<Skill, Boolean> validate() {
        if (this.source == null || this.target == null)
            throw new IllegalStateException("SkillOperationQuery is not ready for validation");
        return operation.validate(this);
    }
}
