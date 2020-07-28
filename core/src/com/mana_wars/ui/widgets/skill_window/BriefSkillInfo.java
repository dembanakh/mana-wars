package com.mana_wars.ui.widgets.skill_window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.widgets.base.BuildableUI;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class BriefSkillInfo extends Window implements BuildableUI {

    private final Label skillName;
    private final Label skillDescription;

    public BriefSkillInfo(String title, Skin skin) {
        super(title, skin);
        this.skillName = new Label("", skin);
        this.skillName.setAlignment(Align.center);
        this.skillName.setColor(Color.BLACK);
        this.skillDescription = new Label("", skin);
        this.skillDescription.setAlignment(Align.center | Align.right);
        //this.skillDescription.setFontScale(2.5f);
        this.skillDescription.setColor(Color.BLACK);
        init();
    }

    private void init() {
        getStyle().titleFont.getData().setScale(2);
        setFillParent(false);
        setMovable(false);
        setResizable(false);
        setVisible(false);

        add(skillName).row();
        add(skillDescription).padTop(20).row();
    }

    public void open(Skill skill, float tapX, float tapY) {
        skillName.setText(skill.getName());
        skillDescription.setText(BaseSkillWindow.getDescription(skill));
        pack();

        float actualX = tapX;
        float actualY = tapY;
        if (actualX > SCREEN_WIDTH() - getWidth()) actualX -= getWidth();
        if (actualY > SCREEN_HEIGHT() - getHeight()) actualY -= getHeight();
        setPosition(actualX, actualY);
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    @Override
    public Actor build() {
        pack();
        return this;
    }
}
