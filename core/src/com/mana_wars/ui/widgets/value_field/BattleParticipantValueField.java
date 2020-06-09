package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.widgets.skills_list_2d.List2D;

public class BattleParticipantValueField extends ValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> {

    private Label participantHealth;
    private ProgressBar healthBar;

    private Label participantName;

    private List2D<PassiveSkill> participantPassiveSkills;

    @Override
    public void init() {
        super.init();

        Table field = new Table();

        participantName = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
        participantName.setFillParent(true);
        participantName.setColor(Color.BLACK);
        participantName.setFontScale(4);
        field.add(participantName).row();

        Stack stack = new Stack();
        healthBar = new ProgressBar(0, 100, 1, false,
                new ProgressBar.ProgressBarStyle());
        healthBar.setScale(4);
        stack.add(healthBar);


        participantHealth = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
        participantHealth.setFillParent(true);
        participantHealth.setColor(Color.BLACK);
        participantHealth.setFontScale(4);
        stack.add(participantHealth);
        field.add(stack).row();

        addActor(field);

        //setup participantPassiveSkills
    }

    /*
    @Override
    public void init() {
        super.init();

        participantName = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
        participantName.setFillParent(true);
        participantName.setColor(Color.BLACK);
        participantName.setFontScale(2);
        addActor(participantName);


        Stack stack = new Stack();
        healthBar = new ProgressBar(0, 100, 1, false,
                new ProgressBar.ProgressBarStyle());
        stack.add(healthBar);

        participantHealth = new Label("", new Label.LabelStyle(new BitmapFont(), new Color()));
        participantHealth.setFillParent(true);
        participantHealth.setColor(Color.BLACK);
        participantHealth.setFontScale(2);
        stack.add(participantHealth);
        addActor(stack);

        //setup participantPassiveSkills
    }
     */

    @Override
    public Actor build(final Skin skin) {
        Actor field = super.build(skin);
        participantName.setStyle(skin.get(Label.LabelStyle.class));
        participantHealth.setStyle(skin.get(Label.LabelStyle.class));
        healthBar.setStyle(skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        return field;
    }

    @Override
    public synchronized void accept(Integer value) {
        participantHealth.setText(value);
        healthBar.setValue(value);
    }

    @Override
    public void setInitialData(Data data) {
        participantName.setText(data.name);
        healthBar.setRange(0, data.initialHealth);
        //participantPassiveSkills.setItems(data.passiveSkills);
    }

    public static class Data {
        private final String name;
        private final int initialHealth;
        private final Iterable<PassiveSkill> passiveSkills;

        public Data(String name, int initialHealth, Iterable<PassiveSkill> passiveSkills) {
            this.name = name;
            this.initialHealth = initialHealth;
            this.passiveSkills = passiveSkills;
        }
    }

}
