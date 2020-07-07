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
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.widgets.base.List2D;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

import java.util.Locale;

public final class BattleParticipantValueField extends ValueField<BattleScreen.BattleParticipantData, Integer> {

    private Label participantHealthLabel;
    private ProgressBar participantHealthBar;

    private Label participantNameLabel;

    private List2D<PassiveSkill> participantPassiveSkillsList;

    private Label healthChangeLabel;

    private Image participantImage;

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;
    private final AssetFactory<String, Texture> imageFactory;

    private final float deltaHealthAnimationDistance;
    private final float deltaHealthAnimationDuration;

    private boolean initializing = false;

    BattleParticipantValueField(TransformApplier transformApplier,
                                final AssetFactory<Integer, TextureRegion> iconFactory,
                                final AssetFactory<Rarity, TextureRegion> frameFactory,
                                final AssetFactory<String, Texture> imageFactory,
                                float deltaHealthAnimationDistance,
                                float deltaHealthAnimationDuration) {
        super(transformApplier);
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.imageFactory = imageFactory;
        this.deltaHealthAnimationDistance = deltaHealthAnimationDistance;
        this.deltaHealthAnimationDuration = deltaHealthAnimationDuration;
    }

    BattleParticipantValueField(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor, TransformApplier transformApplier,
                                final AssetFactory<Integer, TextureRegion> iconFactory,
                                final AssetFactory<Rarity, TextureRegion> frameFactory,
                                final AssetFactory<String, Texture> imageFactory,
                                float deltaHealthAnimationDistance,
                                float deltaHealthAnimationDuration) {
        super(backgroundColor, transformApplier);
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.imageFactory = imageFactory;
        this.deltaHealthAnimationDistance = deltaHealthAnimationDistance;
        this.deltaHealthAnimationDuration = deltaHealthAnimationDuration;
    }

    @Override
    public void init() {
        super.init();

        participantNameLabel = new Label("", UIElementFactory.emptyLabelStyle());
        participantNameLabel.setColor(Color.BLACK);
        participantNameLabel.setFontScale(4);
        field.add(participantNameLabel).top().row();

        Stack stack = new Stack();
        participantHealthBar = new ProgressBar(0, 100, 1, false,
                new ProgressBar.ProgressBarStyle());
        participantHealthBar.setScale(4);
        stack.add(participantHealthBar);

        participantHealthLabel = new Label("", UIElementFactory.emptyLabelStyle());
        participantHealthLabel.setFillParent(true);
        participantHealthLabel.setColor(Color.BLACK);
        participantHealthLabel.setFontScale(4);
        stack.add(participantHealthLabel);
        field.add(stack).top().row();

        healthChangeLabel = new Label("", UIElementFactory.emptyLabelStyle());
        healthChangeLabel.setFontScale(4);
        field.add(healthChangeLabel).top().row();

        participantPassiveSkillsList = UIElementFactory.skillsListWithoutLevel(GameConstants.USER_PASSIVE_SKILL_COUNT,
                iconFactory, frameFactory);
        //participantPassiveSkills.setMinHeight(131.4f); // Do we need this?
        field.add(participantPassiveSkillsList).top().row();

        TextureRegion tempRegion = new TextureRegion(imageFactory.getAsset("player"));
        participantImage = new Image(tempRegion);
        field.add(participantImage).pad(28).top().row();
    }

    @Override
    public Actor build(Skin skin) {
        participantNameLabel.setStyle(skin.get(Label.LabelStyle.class));
        participantHealthLabel.setStyle(skin.get(Label.LabelStyle.class));
        participantHealthBar.setStyle(skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        healthChangeLabel.setStyle(skin.get(Label.LabelStyle.class));
        participantPassiveSkillsList.setStyle(UIElementFactory.emptyListStyle(skin));
        return super.build(skin);
    }

    @Override
    public void accept(Integer value) {
        int lastValue = (int) participantHealthBar.getValue();
        participantHealthLabel.setText(value);
        participantHealthBar.setValue(value);
        if (!initializing)
            updateHealthChangeLabel(value - lastValue);
        else initializing = false;
    }

    public void setInitialData(BattleScreen.BattleParticipantData data) {
        initializing = true;
        participantNameLabel.setText(data.name);
        participantHealthBar.setRange(0, data.initialHealth);
        participantPassiveSkillsList.setItems(data.passiveSkills);
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

}
