package com.mana_wars.model.db.entity.base;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "mobs_skills")
public class DBMobSkill {

    @PrimaryKey()
    @ColumnInfo(name = "mob_skill_id")
    private int id;

    @ColumnInfo(name = "skill_id")
    private int skillID;

    @ColumnInfo(name = "mob_id")
    private int mobID;

    @ColumnInfo(name = "lvl")
    private int lvl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public int getMobID() {
        return mobID;
    }

    public void setMobID(int mobID) {
        this.mobID = mobID;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public static DBMobSkill fromJSON(JSONObject json) throws JSONException {
        DBMobSkill result = new DBMobSkill();
        result.setId(json.getInt("id"));
        result.setMobID(json.getInt("mob_id"));
        result.setSkillID(json.getInt("skill_id"));
        result.setLvl(json.getInt("lvl"));
        return result;
    }

}
