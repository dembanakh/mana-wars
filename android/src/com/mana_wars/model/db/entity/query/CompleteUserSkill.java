package com.mana_wars.model.db.entity.query;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mana_wars.model.db.entity.base.DBSkill;
import com.mana_wars.model.db.entity.base.DBSkillCharacteristic;

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
