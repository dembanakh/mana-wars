package com.mana_wars.model.db.entity.base;

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

    public int getRequiredLvl() {
        return requiredLvl;
    }

    public void setRequiredLvl(int requiredLvl) {
        this.requiredLvl = requiredLvl;
    }

    public static DBDungeon fromJSON(JSONObject json) throws JSONException {
        DBDungeon result = new DBDungeon();
        result.setId(json.getInt("id"));
        result.setName(json.getString("name"));
        result.setRequiredLvl(json.getInt("required_lvl"));
        return result;
    }
}
