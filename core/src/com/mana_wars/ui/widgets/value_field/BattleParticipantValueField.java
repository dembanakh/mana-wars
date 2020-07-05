package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.base.List2D;

import java.util.Locale;

public class BattleParticipantValueField extends ManualTransformValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> {

    private Label participantHealth;
    private ProgressBar healthBar;

    private Label participantName;

    private List2D<PassiveSkill> participantPassiveSkills;

    private Label healthChangeLabel;

    private Image participantImage;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;
    private final AssetFactory<String, Texture> imageFactory;

    private final float deltaHealthAnimationDistance;
    private final float deltaHealthAnimationDuration;

    private boolean initializing = false;

    public BattleParticipantValueField(final AssetFactory<Integer, TextureRegion> iconFactory,
                                       final AssetFactory<Rarity, TextureRegion> frameFactory,
                                       final AssetFactory<String, Texture> imageFactory,
                                       float deltaHealthAnimationDistance,
                                       float deltaHealthAnimationDuration) {
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.imageFactory = imageFactory;
        this.deltaHealthAnimationDistance = deltaHealthAnimationDistance;
        this.deltaHealthAnimationDuration = deltaHealthAnimationDuration;
    }

    @Override
    public void init() {
        super.init();

        participantName = new Label("", UIElementFactory.emptyLabelStyle());
        participantName.setColor(Color.BLACK);
        participantName.setFontScale(4);
        addActor(participantName);

        Stack stack = new Stack();
        healthBar = new ProgressBar(0, 100, 1, false,
                new ProgressBar.ProgressBarStyle());
        healthBar.setScale(4);
        stack.add(healthBar);

        participantHealth = new Label("", UIElementFactory.emptyLabelStyle());
        participantHealth.setFillParent(true);
        participantHealth.setColor(Color.BLACK);
        participantHealth.setFontScale(4);
        stack.add(participantHealth);
        addActor(stack);

        healthChangeLabel = new Label("", UIElementFactory.emptyLabelStyle());
        healthChangeLabel.setFontScale(4);
        addActor(healthChangeLabel);

        participantPassiveSkills = UIElementFactory.skillsListWithoutLevel(GameConstants.USER_PASSIVE_SKILL_COUNT,
                iconFactory, frameFactory);
        //participantPassiveSkills.setMinHeight(131.4f); // Do we need this?
        addActorAndExpandX(participantPassiveSkills);

        TextureRegion tempRegion = new TextureRegion(imageFactory.getAsset("player"));
        participantImage = new Image(tempRegion);
        addActorAndPad(participantImage, 28);
    }

    @Override
    public Actor build(final Skin skin) {
        Actor field = super.build(skin);
        participantName.setStyle(skin.get(Label.LabelStyle.class));
        participantHealth.setStyle(skin.get(Label.LabelStyle.class));
        healthBar.setStyle(skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        healthChangeLabel.setStyle(skin.get(Label.LabelStyle.class));
        participantPassiveSkills.setStyle(UIElementFactory.emptyListStyle(skin));
        return field;
    }

    @Override
    public synchronized void accept(Integer value) {
        int lastValue = (int) healthBar.getValue();
        participantHealth.setText(value);
        healthBar.setValue(value);
        if (!initializing)
            updateHealthChangeLabel(value - lastValue);
        else initializing = false;
    }

    private void updateHealthChangeLabel(int deltaHealth) {
        if (deltaHealth == 0) return;
        else if (deltaHealth > 0) {
            healthChangeLabel.setText(String.format(Locale.US, "+%d", deltaHealth));
            healthChangeLabel.setColor(Color.GREEN);
        } else {
            healthChangeLabel.setText(deltaHealth);
            healthChangeLabel.setColor(Color.RED);
        }
        healthChangeLabel.addAction(Actions.sequence(
                Actions.fadeIn(0),
                Actions.parallel(Actions.moveBy(deltaHealthAnimationDistance, 0,
                        deltaHealthAnimationDuration, Interpolation.pow2InInverse),
                        Actions.fadeOut(deltaHealthAnimationDuration)),
                Actions.moveBy(-deltaHealthAnimationDistance, 0)));
    }

    @Override
    public void setInitialData(Data data) {
        initializing = true;
        participantName.setText(data.name);
        healthBar.setRange(0, data.initialHealth);
        participantPassiveSkills.setItems(data.passiveSkills);
        /*try {
            healthBar.setValue(data.initialHealth);
            participantHealth.setText(data.initialHealth);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
