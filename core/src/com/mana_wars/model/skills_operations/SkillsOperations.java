package com.mana_wars.model.skills_operations;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.base.Rarity;
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
 * swap 2 skills iff both are instanceof the same Skill subclass {{and merge is not possible}} (Skill.Empty excluded);
 * move 1 skill iff it is of a proper subclass and "second" is null or Skill.Empty.
 * Speaking of the table-level restrictions:
 *     |    ALL   |    ACT   |    PAS   |
 * --------------------------------------
 * ALL | ME       | ME,SW,MO | ME,SW,MO |
 * ACT | ME,SW,MO | ME,SW,MO |          |
 * PAS | ME,SW,MO |          | ME,SW,MO |
 *
 * Usage example:
 * if (SkillsOperations.can(MERGE).from(ALL_SKILLS).to(PASSIVE_SKILLS).validate()
 *                                  .from(skillSource).to(skillTarget).validate()) {}
 */
public enum SkillsOperations {
    MERGE {
        @Override
        protected Boolean validate(SkillOperationQuery query) {
            return query.source != null && query.target != null &&
                    query.source.getName().equals(query.target.getName()) &&
                    query.source.getLevel() == query.target.getLevel();
        }

        @Override
        protected OperationQuery<Skill, Boolean> validate(TableOperationQuery query) {
            OperationQuery<Skill, Boolean> resultQuery = super.validate(query);
            return (resultQuery == null) ? new SkillOperationQuery(query) : resultQuery;
        }
    }, SWAP {
        @Override
        protected Boolean validate(SkillOperationQuery query) {
            if (query.source == null || query.target == null || query.target.getRarity() == Rarity.EMPTY) return false;
            if (query.tableQuery.source == query.tableQuery.target)
                return query.source.getRarity() != Rarity.EMPTY && query.target.getRarity() != Rarity.EMPTY;
            return (query.target instanceof ActiveSkill && query.source instanceof ActiveSkill) ||
                    (query.target instanceof PassiveSkill && query.source instanceof PassiveSkill);
        }
        @Override
        protected OperationQuery<Skill, Boolean> validate(TableOperationQuery query) {
            OperationQuery<Skill, Boolean> resultQuery = super.validate(query);
            if (resultQuery == null) {
                if (query.source == SkillTable.ALL_SKILLS && query.target == SkillTable.ALL_SKILLS)
                    return new EmptySkillOperationQuery();
                return new SkillOperationQuery(query);
            } else return resultQuery;
        }
    }, MOVE {
        @Override
        protected Boolean validate(SkillOperationQuery query) {
            if (query.tableQuery.source == query.tableQuery.target)
                return query.target.getRarity() == Rarity.EMPTY;
            if (query.tableQuery.source == SkillTable.ALL_SKILLS) {
                if (query.tableQuery.target == SkillTable.ACTIVE_SKILLS)
                    return (query.source instanceof ActiveSkill) && (query.target.getRarity() == Rarity.EMPTY);
                if (query.tableQuery.target == SkillTable.PASSIVE_SKILLS)
                    return (query.source instanceof PassiveSkill) && (query.target.getRarity() == Rarity.EMPTY);
            }
            return query.target == null;
        }

        @Override
        protected OperationQuery<Skill, Boolean> validate(TableOperationQuery query) {
            OperationQuery<Skill, Boolean> resultQuery = super.validate(query);
            if (resultQuery == null) {
                if (query.source == query.target && query.source == SkillTable.ALL_SKILLS)
                    return new EmptySkillOperationQuery();
                return new SkillOperationQuery(query);
            } else return resultQuery;
        }
    };


    public static OperationQuery<SkillTable, OperationQuery<Skill, Boolean>> can(SkillsOperations op) {
        return new TableOperationQuery(op);
    }

    protected abstract Boolean validate(SkillOperationQuery query);
    protected OperationQuery<Skill, Boolean> validate(TableOperationQuery query) {
        if (query.source == SkillTable.ACTIVE_SKILLS && query.target == SkillTable.PASSIVE_SKILLS)
            return new EmptySkillOperationQuery();
        if (query.source == SkillTable.PASSIVE_SKILLS && query.target == SkillTable.ACTIVE_SKILLS)
            return new EmptySkillOperationQuery();
        return null;
    }

}
