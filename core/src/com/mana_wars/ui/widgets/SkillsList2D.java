package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;

public class SkillsList2D extends List2D<Skill> {

    private final boolean ordered;

    private AssetFactory<Integer, TextureRegion> iconFactory;
    private AssetFactory<Rarity, TextureRegion> frameFactory;

    public SkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                        AssetFactory<Rarity, TextureRegion> frameFactory, boolean ordered) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory, ordered);
    }

    public SkillsList2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                        AssetFactory<Rarity, TextureRegion> frameFactory, boolean ordered) {
        super(style, cols);
        this.ordered = ordered;
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.selection.setDisabled(true);
    }

    @Override
    protected void drawItem(Batch batch, BitmapFont font, int index, Skill item, float x, float y, float width, float height) {
        TextureRegion icon = iconFactory.getAsset(item.getIconID());
        TextureRegion frame = frameFactory.getAsset(item.getRarity());
        String text = String.valueOf(item.getLevel());

        float iconOffsetX = (width - icon.getRegionWidth()) / 2;
        float iconOffsetY = (height - icon.getRegionHeight()) / 2;
        float frameOffsetX = (width - frame.getRegionWidth()) / 2;
        float frameOffsetY = (height - frame.getRegionHeight()) / 2;

        batch.draw(icon, x + iconOffsetX, y + iconOffsetY);
        batch.draw(frame, x + frameOffsetX, y + frameOffsetY);

        font.setColor(Color.BLACK);
        font.getData().setScale(2);
        font.draw(batch, text, x + width / 2, y + frameOffsetY, 0, text.length(),
                width, alignment, false, "");
    }

    @Override
    public int insert(int index, Skill item) {
        if (item == null) throw new IllegalArgumentException("SkillsList2D.insert: item cannot be null");
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
            if (items.get(index).data == Skill.Empty) items.get(index).data = item;
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
            items.get(index).data = Skill.Empty;
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
