package com.mana_wars.model.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "skill_characteristic")
public class DBSkillCharacteristic {

    @PrimaryKey
    @ColumnInfo(name = "skill_char_id")
    private int id;

    @ColumnInfo(name = "skill_id")
    private int skillID;

    @ColumnInfo(name = "value")
    private int value;

    @ColumnInfo(name = "type")
    private int type;

    /**
     * true INCREASE
     * false DECREASE
     */
    @ColumnInfo(name = "change_type")
    private boolean changeType;

    @ColumnInfo(name = "target")
    private int target;

    @ColumnInfo(name = "upgrade_function")
    private String upgradeFunction;

    @ColumnInfo(name = "level_multiplier")
    private int levelMultiplier;


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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getChangeType() {
        return changeType;
    }

    public void setChangeType(boolean changeType) {
        this.changeType = changeType;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getUpgradeFunction() {
        return upgradeFunction;
    }

    public void setUpgradeFunction(String upgradeFunction) {
        this.upgradeFunction = upgradeFunction;
    }

    public int getLevelMultiplier() {
        return levelMultiplier;
    }

    public void setLevelMultiplier(int levelMultiplier) {
        this.levelMultiplier = levelMultiplier;
    }

    public DBSkillCharacteristic() {
    }

    public static DBSkillCharacteristic fromJSON(JSONObject json) throws JSONException {
        DBSkillCharacteristic result = new DBSkillCharacteristic();
        result.setId(json.getInt("id"));
        result.setSkillID(json.getInt("skill_id"));
        result.setValue(json.getInt("value"));
        result.setType(json.getInt("characteristic"));
        result.setChangeType(json.getInt("value_change") == 1);
        result.setTarget(json.getInt("target"));
        result.setUpgradeFunction(json.getString("upgrade_function"));
        result.setLevelMultiplier(json.getInt("level_multiplier"));
        return result;
    }
}
