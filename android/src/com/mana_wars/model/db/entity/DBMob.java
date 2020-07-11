package com.mana_wars.model.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "mobs")
public class DBMob {

    @PrimaryKey
    @ColumnInfo(name = "mob_id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "dungeon_ref_id")
    private int dungeonId;

    @ColumnInfo(name = "init_health")
    private int initialHealth;

    @ColumnInfo(name = "mana_reward")
    private int manaReward;

    @ColumnInfo(name = "experience_reward")
    private int experienceReward;

    @ColumnInfo(name = "case_probability_reward")
    private int caseProbabilityReward;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) {
        this.dungeonId = dungeonId;
    }

    public int getInitialHealth() {
        return initialHealth;
    }

    public void setInitialHealth(int initialHealth) {
        this.initialHealth = initialHealth;
    }

    public int getManaReward() {
        return manaReward;
    }

    public void setManaReward(int manaReward) {
        this.manaReward = manaReward;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public void setExperienceReward(int experienceReward) {
        this.experienceReward = experienceReward;
    }

    public int getCaseProbabilityReward() {
        return caseProbabilityReward;
    }

    public void setCaseProbabilityReward(int caseProbabilityReward) {
        this.caseProbabilityReward = caseProbabilityReward;
    }

    public static DBMob fromJSON(JSONObject json) throws JSONException {
        DBMob result = new DBMob();
        result.setId(json.getInt("id"));
        result.setName(json.getString("name"));
        result.setDungeonId(json.getInt("dungeon_id"));
        result.setInitialHealth(json.getInt("init_health"));
        result.setManaReward(json.getInt("mana_reward"));
        result.setExperienceReward(json.getInt("experience_reward"));
        result.setCaseProbabilityReward(json.getInt("case_probability_reward"));
        return result;
    }
}
