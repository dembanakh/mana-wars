package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.skills.Skill;

class TableOperationQuery implements OperationQuery<SkillTable, OperationQuery<Skill, Boolean>> {
    final SkillsOperations operation;
    SkillTable source;
    SkillTable target;

    private boolean sourceSet = false;
    private boolean targetSet = false;

    TableOperationQuery(SkillsOperations operation) {
        this.operation = operation;
    }

    public OperationQuery<SkillTable, OperationQuery<Skill, Boolean>> from(SkillTable source) {
        if (this.sourceSet) throw new IllegalStateException("TableOperationQuery cannot have 2 sources");
        this.source = source;
        this.sourceSet = true;
        return this;
    }

    public OperationQuery<SkillTable, OperationQuery<Skill, Boolean>> to(SkillTable target) {
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
