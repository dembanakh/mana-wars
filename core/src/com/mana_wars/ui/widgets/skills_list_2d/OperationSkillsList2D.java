package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;

public class OperationSkillsList2D extends StaticSkillsList2D<Skill> {

    private final boolean ordered;

    public OperationSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                 AssetFactory<Rarity, TextureRegion> frameFactory, boolean ordered) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory, ordered);
    }

    public OperationSkillsList2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                 AssetFactory<Rarity, TextureRegion> frameFactory, boolean ordered) {
        super(style, cols, iconFactory, frameFactory);
        this.ordered = ordered;
    }

    @Override
    public int insert(int index, Skill item) {
        if (item == null)
            throw new IllegalArgumentException("SkillsList2D.insert: item cannot be null");
        if (!ordered && (index < 0 || index >= items.size))
            throw new IllegalArgumentException("SkillsList2D.insert: index is not legal");

        if (ordered) {
            int indexToInsert = 0;
            boolean found = false;
            int availableID = -1;
            for (List2DItem<Skill> listItem : items) {
                if (listItem.index > availableID) availableID = listItem.index;
                if (listItem.data.compareTo(item) <= 0) found = true;
                if (!found) indexToInsert++;
            }
            items.insert(indexToInsert, new List2DItem<>(availableID + 1, item));
            return indexToInsert;
        } else {
            if (items.get(index).data.getRarity() == Rarity.EMPTY) items.get(index).data = item;
            else super.insert(index, item);
            return index;
        }
    }

    @Override
    public Skill removeIndex(int index) {
        if (ordered) {
            return super.removeIndex(index);
        } else {
            Skill result = items.get(index).data;
            items.get(index).data = Skill.getEmpty();
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
