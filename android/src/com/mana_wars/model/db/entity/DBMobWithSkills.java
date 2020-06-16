package com.mana_wars.model.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DBMobWithSkills {

    @Embedded
    public DBMob mob;

    @Relation(
            parentColumn = "mob_id",
            entityColumn = "mob_id",
            entity = DBMobSkill.class
    )
    public List<DBMobSkillWithCharacteristics> skills;

}
