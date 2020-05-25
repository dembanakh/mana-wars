package com.mana_wars.model.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "skills")
public class DBSkill {

    @PrimaryKey
    @ColumnInfo(name = "skill_id")
    private int id;

    @ColumnInfo(name = "rarity")
    private int rarity;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "manacost")
    private int manaCost;

    @ColumnInfo(name = "is_active")
    private boolean isActive;

    @ColumnInfo(name = "cast_time", defaultValue = "0")
    private double castTime; //only for active skills

    @ColumnInfo(name = "cooldown", defaultValue = "0")
    private double cooldown; //only for active skills


    public int getId() {
        return id;
    }

    public int getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public boolean isActive() {
        return isActive;
    }

    public double getCastTime() {
        return castTime;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCastTime(double castTime) {
        this.castTime = castTime;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public static DBSkill fromJSON(JSONObject json) throws JSONException {
        DBSkill result = new DBSkill();
        result.setId(json.getInt("id"));
        result.setName(json.getString("name"));
        result.setRarity(json.getInt("rarity"));
        result.setManaCost(json.getInt("manacost"));
        result.setActive(json.getBoolean("is_active"));
        result.setCastTime(result.isActive?json.getDouble("cast_time"):0);
        result.setCooldown(result.isActive?json.getDouble("cooldown"):0);
        return result;
    }
}
