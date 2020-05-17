package com.mana_wars.model.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_skills")
public class UserSkill {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_skill_id")
    private int id;

    @ColumnInfo(name = "lvl")
    private int lvl;

    @ColumnInfo(name = "skill_ref_id")
    private int skillID;

    public UserSkill(){}

    @Ignore
    public UserSkill(int lvl, int skillID){
        this.lvl = lvl;
        this.skillID = skillID;
    }

    public int getId() {
        return id;
    }

    public int getLvl() {
        return lvl;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }
}
