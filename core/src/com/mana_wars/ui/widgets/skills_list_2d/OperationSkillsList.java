package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.InputProcessor;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.ui.widgets.base.BuildableUI;

public interface OperationSkillsList<T extends Skill> extends BuildableUI {
    void setItems(Iterable<? extends T> skills);
    void clearSelection();
    Iterable<? extends Skill> getItems();
    T getItemAt(float x, float y);
    SkillTable getTableType();
    int getItemIndexAt(float x, float y);
    T getItem(int index);
    void setSelectedIndices(Iterable<? extends Integer> mergeableIndices);
    int insert(int index, Skill skill);
    T removeIndex(int index);
    void finishMoveOperation(int index, T skill);

    InputProcessor getDoubleTapProcessor();
}
