package com.mana_wars.model.db.entity.query;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
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

    @ColumnInfo(name = "chosen_id", defaultValue = "0")
    private int chosen_id;

    public UserSkill() {
    }

    @Ignore
    public UserSkill(int lvl, int skillID) {
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

    public int getChosen_id() {
        return chosen_id;
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

    public void setChosen_id(int chosen_id) {
        this.chosen_id = chosen_id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserSkill)) return false;
        UserSkill other = (UserSkill)obj;
        return lvl == other.lvl && skillID == other.skillID;
    }
}
