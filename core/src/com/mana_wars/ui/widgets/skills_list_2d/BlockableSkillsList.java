package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mana_wars.model.entity.skills.Skill;

public interface BlockableSkillsList<T extends Skill> {
    Actor toActor();
    void update(float delta);
    void blockSkills(int appliedSkillIndex);
    void setItems(Iterable<? extends T> items);
}
