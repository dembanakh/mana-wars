package com.mana_wars.model.db.entity.query;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mana_wars.model.db.entity.base.DBDungeon;
import com.mana_wars.model.db.entity.base.DBDungeonRoundDescription;

import java.util.List;

public class DBDungeonWithRoundsDescription {

    @Embedded
    public DBDungeon dungeon;

    @Relation(
            parentColumn = "dungeon_id",
            entityColumn = "dungeon_ref_id"
    )
    public List<DBDungeonRoundDescription> roundDescriptions;

}
