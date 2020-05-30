package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.skills.Skill;

class TableOperationQuery implements OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> {
    final SkillsOperations operation;
    SkillsOperations.Table source;
    SkillsOperations.Table target;

    private boolean sourceSet = false;
    private boolean targetSet = false;

    TableOperationQuery(SkillsOperations operation) {
        this.operation = operation;
    }

    public OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> from(SkillsOperations.Table source) {
        if (this.sourceSet) throw new IllegalStateException("TableOperationQuery cannot have 2 sources");
        this.source = source;
        this.sourceSet = true;
        return this;
    }

    public OperationQuery<SkillsOperations.Table, OperationQuery<Skill, Boolean>> to(SkillsOperations.Table target) {
        if (!this.sourceSet) throw new IllegalStateException("TableOperationQuery has no source");
        if (this.targetSet) throw new IllegalStateException("TableOperationQuery cannot have 2 targets");
        this.target = target;
        this.targetSet = true;
        return this;
    }

    public OperationQuery<Skill, Boolean> validate() {
        if (!this.sourceSet || !this.targetSet)
            throw new IllegalStateException("SkillOperationQuery is not ready for validation");
        return operation.validate(this);
    }
}
