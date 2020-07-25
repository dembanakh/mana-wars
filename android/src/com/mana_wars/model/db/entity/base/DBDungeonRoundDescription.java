package com.mana_wars.model.db.entity.base;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "dungeon_rounds")
public class DBDungeonRoundDescription {

    @PrimaryKey
    @ColumnInfo(name = "drd_id")
    private int id;

    @ColumnInfo(name = "dungeon_ref_id")
    private int dungeonId;

    @ColumnInfo(name = "round")
    private int round;

    @ColumnInfo(name = "min_op")
    private int minOpponents;

    @ColumnInfo(name = "max_op")
    private int maxOpponents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) {
        this.dungeonId = dungeonId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMinOpponents() {
        return minOpponents;
    }

    public void setMinOpponents(int minOpponents) {
        this.minOpponents = minOpponents;
    }

    public int getMaxOpponents() {
        return maxOpponents;
    }

    public void setMaxOpponents(int maxOpponents) {
        this.maxOpponents = maxOpponents;
    }

    public static DBDungeonRoundDescription fromJSON(JSONObject json) throws JSONException {
        DBDungeonRoundDescription result = new DBDungeonRoundDescription();
        result.setId(json.getInt("id"));
        result.setDungeonId(json.getInt("dungeon_id"));
        result.setRound(json.getInt("round"));
        result.setMinOpponents(json.getInt("min"));
        result.setMaxOpponents(json.getInt("max"));
        return result;
    }
}
