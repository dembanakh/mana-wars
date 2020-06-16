package com.mana_wars.model.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

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
