package com.mana_wars.model.db.entity.query;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mana_wars.model.db.entity.base.DBMob;
import com.mana_wars.model.db.entity.base.DBMobSkill;

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
