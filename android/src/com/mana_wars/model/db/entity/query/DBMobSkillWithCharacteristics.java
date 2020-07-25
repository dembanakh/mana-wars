package com.mana_wars.model.db.entity.query;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mana_wars.model.db.entity.base.DBMobSkill;
import com.mana_wars.model.db.entity.base.DBSkill;

public class DBMobSkillWithCharacteristics {

    @Embedded
    public DBMobSkill dbMobSkill;

    @Relation(
            parentColumn = "skill_id",
            entity = DBSkill.class,
            entityColumn = "skill_id"
    )
    public DBSkillWithCharacteristics skill;
}
