package com.mana_wars.model.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DBSkillWithCharacteristics {

    @Embedded
    public DBSkill skill;

    @Relation(
            parentColumn = "skill_id",
            entityColumn = "skill_id"
    )
    public List<DBSkillCharacteristic> characteristics;

}
