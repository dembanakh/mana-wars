package com.mana_wars.presentation.view;

import com.mana_wars.model.entity.dungeon.Dungeon;

import java.util.List;

public interface DungeonsView extends BaseView {
    void setDungeonsList(List<Dungeon> dungeons);
    void disableDungeons(int userLevel, boolean insufficientManaAmount);
}
