package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.ui.widgets.base.List2D;
import com.mana_wars.ui.widgets.base.ListItemDrawer;
import com.mana_wars.ui.widgets.skill_window.BriefSkillInfo;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;

public class OperationSkillsList2D implements OperationSkillsList<Skill>, GestureDetector.GestureListener {

    private final List2D<Skill> list;
    private final BriefSkillInfo skillInfo;

    private final boolean ordered;
    private final SkillTable tableType;

    public OperationSkillsList2D(Skin skin, ListItemDrawer<Skill> listItemDrawer, int cols, boolean ordered,
                                 SkillTable tableType, BriefSkillInfo skillInfo) {
        this(skin.get(List.ListStyle.class),
                listItemDrawer, cols, ordered, tableType, skillInfo);
    }

    public OperationSkillsList2D(List.ListStyle style,
                                 ListItemDrawer<Skill> listItemDrawer, int cols,
                                 boolean ordered, SkillTable tableType,
                                 BriefSkillInfo skillInfo) {
        this.skillInfo = skillInfo;
        this.list = new List2D<>(style, listItemDrawer, cols);
        this.ordered = ordered;
        this.tableType = tableType;
        list.setUserObject(tableType);
    }

    @Override
    public int insert(int index, Skill item) {
        if (item == null)
            throw new IllegalArgumentException("SkillsList2D.insert: item cannot be null");
        if (!ordered && (index < 0 || index >= list.size()))
            throw new IllegalArgumentException("SkillsList2D.insert: index is not legal");

        if (ordered) {
            int indexToInsert;
            for (indexToInsert = 0; indexToInsert < list.size(); ++indexToInsert) {
                if (list.getItem(indexToInsert).compareTo(item) <= 0) break;
            }
            list.insert(indexToInsert, item);
            return indexToInsert;
        } else {
            if (list.getItem(index).getRarity() == Rarity.EMPTY) list.setItem(index, item);
            else list.insert(index, item);
            return index;
        }
    }

    @Override
    public Skill removeIndex(int index) {
        if (ordered) {
            return list.removeIndex(index);
        } else {
            Skill result = list.getItem(index);
            list.setItem(index, Skill.getEmpty());
            return result;
        }
    }

    @Override
    public void finishMoveOperation(int index, Skill skill) {
        if (ordered) {
            list.setItem(index, skill);
        } else {
            list.insert(index, skill);
        }
    }

    @Override
    public InputProcessor getDoubleTapProcessor() {
        return new GestureDetector(this);
    }

    @Override
    public Actor build() {
        return list;
    }

    @Override
    public void setItems(Iterable<? extends Skill> skills) {
        list.setItems(skills);
    }

    @Override
    public void clearSelection() {
        list.setSelectedIndex(-1);
    }

    @Override
    public Iterable<? extends Skill> getItems() {
        return list.getItemsCopy();
    }

    @Override
    public Skill getItemAt(float x, float y) {
        return list.getItemAt(x, y);
    }

    @Override
    public SkillTable getTableType() {
        return tableType;
    }

    @Override
    public int getItemIndexAt(float x, float y) {
        return list.getItemIndexAt(x, y);
    }

    @Override
    public Skill getItem(int index) {
        return list.getItem(index);
    }

    @Override
    public void setSelectedIndices(Iterable<? extends Integer> mergeableIndices) {
        list.setSelectedIndices(mergeableIndices);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (count == 1) {
            skillInfo.hide();
        }
        else if (count == 2) {
            y = SCREEN_HEIGHT() - y; // another coordinate system
            Vector2 localTapPosition = list.stageToLocalCoordinates(new Vector2(x, y));
            Skill tappedSkill = getItemAt(localTapPosition.x, localTapPosition.y);
            if (tappedSkill != null) skillInfo.open(tappedSkill, x, y);
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
