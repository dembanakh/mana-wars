package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.widgets.base.List2D;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

public class OperationSkillsList2D extends List2D<Skill> {

    private final boolean ordered;

    public OperationSkillsList2D(Skin skin, ListItemDrawer<Skill> listItemDrawer, int cols, boolean ordered) {
        this(skin.get(List.ListStyle.class), listItemDrawer, cols, ordered);
    }

    public OperationSkillsList2D(List.ListStyle style, ListItemDrawer<Skill> listItemDrawer, int cols, boolean ordered) {
        super(style, listItemDrawer, cols);
        this.ordered = ordered;
    }

    @Override
    public int insert(int index, Skill item) {
        if (item == null)
            throw new IllegalArgumentException("SkillsList2D.insert: item cannot be null");
        if (!ordered && (index < 0 || index >= size()))
            throw new IllegalArgumentException("SkillsList2D.insert: index is not legal");

        if (ordered) {
            int indexToInsert;
            for (indexToInsert = 0; indexToInsert < size(); ++indexToInsert) {
                if (getItem(indexToInsert).compareTo(item) <= 0) break;
            }
            super.insert(indexToInsert, item);
            return indexToInsert;
        } else {
            if (getItem(index).getRarity() == Rarity.EMPTY) setItem(index, item);
            else super.insert(index, item);
            return index;
        }
    }

    @Override
    public Skill removeIndex(int index) {
        if (ordered) {
            return super.removeIndex(index);
        } else {
            Skill result = getItem(index);
            setItem(index, Skill.getEmpty());
            return result;
        }
    }

    /*
    /*
     * Restore elements order in the list providing that items[index] is the only item out of order.
     * Returns the index of the element after realignment.
     /
    @Override
    public int onItemChangeAt(int index) {
        if (items.size <= 1) return index;

        List2DItem<Skill> item = items.get(index);
        boolean goDown;
        if (index == 0) {
            if (item.data.compareTo(items.get(1).data) >= 0) return index;
            else goDown = true;
        } else if (index == items.size - 1) {
            if (item.data.compareTo(items.get(items.size - 2).data) <= 0) return index;
            else goDown = false;
        } else {
            if (item.data.compareTo(items.get(index + 1).data) < 0) goDown = true;
            else if (item.data.compareTo(items.get(index - 1).data) > 0) goDown = false;
            else return index;
        }

        if (goDown) {
            int indexEnd = index + 1;
            for (List2DItem<Skill> current = items.get(indexEnd);
                 indexEnd < items.size && item.data.compareTo(current.data) < 0;
                 indexEnd++, current = items.get(indexEnd));
            System.arraycopy(items.items, index + 1, items.items, index, indexEnd - index);
            items.set(indexEnd, item);
            return indexEnd;
        } else {
            int indexStart = index - 1;
            for (; indexStart >= 0 && item.data.compareTo(items.get(indexStart).data) > 0; indexStart--);
            indexStart++;
            System.arraycopy(items.items, indexStart, items.items, indexStart + 1, index - indexStart);
            items.set(indexStart, item);
            return indexStart;
        }
    }
    */

}
