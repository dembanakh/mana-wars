package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.skills_list_2d.List2D;
import com.mana_wars.ui.widgets.skills_list_2d.StaticSkillsList2D;

public class BattleParticipantValueField extends ValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> {

    private Label participantHealth;
    private ProgressBar healthBar;

    private Label participantName;

    private List2D<PassiveSkill> participantPassiveSkills;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    public BattleParticipantValueField(final AssetFactory<Integer, TextureRegion> iconFactory,
                                       final AssetFactory<Rarity, TextureRegion> frameFactory) {
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
    }

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

        // TODO: tune SkillsList2D size
        participantPassiveSkills = new StaticSkillsList2D<>(new List.ListStyle(),
                GameConstants.USER_PASSIVE_SKILL_COUNT, iconFactory, frameFactory);
        field.add(participantPassiveSkills).expandX().row();

        addActor(field);
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
        participantPassiveSkills.setStyle(skin.get("default", List.ListStyle.class));
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
        participantPassiveSkills.setItems(data.passiveSkills);
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
