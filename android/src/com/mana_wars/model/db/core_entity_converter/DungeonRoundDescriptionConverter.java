package com.mana_wars.model.db.core_entity_converter;

import com.mana_wars.model.db.entity.base.DBDungeonRoundDescription;
import com.mana_wars.model.entity.dungeon.DungeonRoundDescription;

public class DungeonRoundDescriptionConverter {

    public static DungeonRoundDescription toDungeonRoundsDescription(DBDungeonRoundDescription drd) {
        return new DungeonRoundDescription(false, drd.getMinOpponents(), drd.getMaxOpponents());
    }
}
