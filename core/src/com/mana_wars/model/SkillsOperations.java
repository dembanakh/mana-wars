package com.mana_wars.model;

import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;

/*
 * Possible operations on the Skills (mainly in the SkillsView):
 * merge 2 skills,
 * swap 2 skills,
 * move 1 skill.
 * Generally, the rules are:
 * merge 2 skills iff they have the same name and the same level (Skill.Empty excluded);
 * swap 2 skills iff both are instanceof the same Skill subclass and merge is not possible (Skill.Empty excluded);
 * move 1 skill iff it is not Skill.Empty and "second" is null or Skill.Empty.
 * Speaking of the table-level restrictions:
 *     |    ALL   |    ACT   |    PAS   |
 * --------------------------------------
 * ALL | ME       | ME,SW,MO | ME,SW,MO |
 * ACT | ME,SW,MO | ME,SW,MO |          |
 * PAS | ME,SW,MO |          | ME,SW,MO |
 *
 * Usage example:
 * if (SkillsOperations.can(MERGE).from(ALL_SKILLS).to(PASSIVE_SKILLS).from(skillSource).to(skillTarget)) {}
 */
public enum SkillsOperations {
    MERGE {
        @Override
        protected boolean validate(SkillOperationQuery query) {
            return query.source != null && query.target != null &&
                    query.source.getName().equals(query.target.getName()) &&
                    query.source.getLevel() == query.target.getLevel();
        }

        @Override
        protected SkillOperationQuery validate(TableOperationQuery query) {
            SkillOperationQuery resultQuery = super.validate(query);
            return (resultQuery == null) ? new SkillOperationQuery(query) : resultQuery;
        }
    }, SWAP {
        @Override
        protected boolean validate(SkillOperationQuery query) {
            if (query.source == null || query.target == null) return false;
            if (query.tableQuery.source == query.tableQuery.target)
                return query.source != Skill.Empty && query.target != Skill.Empty;
            return (query.target instanceof ActiveSkill && query.source instanceof ActiveSkill) ||
                    (query.target instanceof PassiveSkill && query.source instanceof PassiveSkill);
        }
        @Override
        protected SkillOperationQuery validate(TableOperationQuery query) {
            SkillOperationQuery resultQuery = super.validate(query);
            if (resultQuery == null) {
                if (query.source == Table.ALL_SKILLS && query.target == Table.ALL_SKILLS)
                    return new SkillOperationQuery();
                return new SkillOperationQuery(query);
            } else return resultQuery;
        }
    }, MOVE {
        @Override
        protected boolean validate(SkillOperationQuery query) {
            if (query.tableQuery.source == query.tableQuery.target) return true;
            if (query.tableQuery.source == Table.ALL_SKILLS) {
                if (query.tableQuery.target == Table.ACTIVE_SKILLS)
                    return (query.source instanceof ActiveSkill) && (query.target == Skill.Empty);
                if (query.tableQuery.target == Table.PASSIVE_SKILLS)
                    return (query.source instanceof PassiveSkill) && (query.target == Skill.Empty);
            }
            return query.target == null;
        }

        @Override
        protected SkillOperationQuery validate(TableOperationQuery query) {
            SkillOperationQuery resultQuery = super.validate(query);
            if (resultQuery == null) {
                if (query.source == query.target && query.source == Table.ALL_SKILLS)
                    return new SkillOperationQuery();
                return new SkillOperationQuery(query);
            } else return resultQuery;
        }
    };

    public enum Table {
        ALL_SKILLS, ACTIVE_SKILLS, PASSIVE_SKILLS
    }

    public static TableOperationQuery can(SkillsOperations op) {
        return new TableOperationQuery(op);
    }

    public static class TableOperationQuery {
        private final SkillsOperations operation;
        private Table source;
        private Table target;

        private TableOperationQuery(SkillsOperations operation) {
            this.operation = operation;
        }

        public TableOperationQuery from(Table source) {
            this.source = source;
            return this;
        }

        public SkillOperationQuery to(Table target) {
            if (this.source == null) throw new IllegalStateException("TableOperationQuery has no source");
            this.target = target;
            return operation.validate(this);
        }
    }

    public static class SkillOperationQuery {
        private TableOperationQuery tableQuery;
        private Skill source;
        private Skill target;

        private boolean invalid = false;

        boolean isValid() { return !invalid; }

        private SkillOperationQuery(TableOperationQuery tableQuery) {
            this.tableQuery = tableQuery;
        }

        /*
         * Creates an invalid query.
         */
        private SkillOperationQuery() {
            this.invalid = true;
        }

        public SkillOperationQuery from(Skill source) {
            this.source = source;
            return this;
        }

        public boolean to(Skill target) {
            if (this.source == null) throw new IllegalStateException("SkillOperationQuery has no source");
            if (invalid) return false;
            this.target = target;
            return tableQuery.operation.validate(this);
        }
    }

    protected abstract boolean validate(SkillOperationQuery query);
    protected SkillOperationQuery validate(TableOperationQuery query) {
        if (query.source == Table.ACTIVE_SKILLS && query.target == Table.PASSIVE_SKILLS)
            return new SkillOperationQuery();
        if (query.source == Table.PASSIVE_SKILLS && query.target == Table.ACTIVE_SKILLS)
            return new SkillOperationQuery();
        return null;
    }

}
