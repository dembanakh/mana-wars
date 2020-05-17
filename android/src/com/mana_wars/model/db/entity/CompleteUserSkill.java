package com.mana_wars.model.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CompleteUserSkill {

    @Embedded
    public UserSkill userSkill;

    @Relation(
            parentColumn = "skill_ref_id",
            entityColumn = "skill_id"
    )
    public DBSkill skill;

    @Relation(
            parentColumn = "skill_ref_id",
            entityColumn = "skill_id"
    )
    public List<DBSkillCharacteristic> characteristics;

}
