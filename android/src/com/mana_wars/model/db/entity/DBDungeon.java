package com.mana_wars.model.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "dungeons")
public class DBDungeon {

    @PrimaryKey
    @ColumnInfo(name = "dungeon_id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "required_lvl")
    private int requiredLvl;

    @ColumnInfo(name = "rounds")
    private int rounds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getRounds() {
        return rounds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredLvl() {
        return requiredLvl;
    }

    public void setRequiredLvl(int requiredLvl) {
        this.requiredLvl = requiredLvl;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public static DBDungeon fromJSON(JSONObject json) throws JSONException {
        DBDungeon result = new DBDungeon();
        result.setId(json.getInt("id"));
        result.setName(json.getString("name"));
        result.setRequiredLvl(json.getInt("required_lvl"));
        result.setRounds(json.getInt("rounds"));
        return result;
    }
}
