package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.factory.LocalizedStringFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

import java.util.ArrayList;
import java.util.List;

import static com.mana_wars.ui.UIStringConstants.BATTLE_SCREEN.CHANGE_ENEMY_KEY;

public class ChangeEnemyButton implements BuildableUI {

    private static final float POPPING_ANIMATION_SCALE = 0.125f;

    private final Button button;

    private int castTimeCoefficient = 0;
    private double blockTimeLeft;
    private final List<Double> skillCastTimes = new ArrayList<>();

    public ChangeEnemyButton(Skin skin, LocalizedStringFactory localizedStringFactory, Runnable onClick) {
        this.button = new TextButton(localizedStringFactory.get(CHANGE_ENEMY_KEY), skin);
        this.button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClick.run();
            }
        });
        this.button.setOrigin(Align.center);
        blockTimeLeft = 0d;
    }

    public void update(float delta) {
        if (blockTimeLeft <= 0d) return;

        blockTimeLeft -= delta;
        if (blockTimeLeft <= 0d) {
            enableButton();
        }
    }

    public void setSkills(Iterable<? extends ActiveSkill> skills) {
        skillCastTimes.clear();
        for (ActiveSkill skill : skills) skillCastTimes.add(skill.getCastTime(castTimeCoefficient));
    }

    public void block(int appliedSkillIndex) {
        disableButton();
        blockTimeLeft = Math.max(blockTimeLeft, skillCastTimes.get(appliedSkillIndex));
    }

    private void disableButton() {
        button.setTransform(false);
        button.setDisabled(true);
        button.setTouchable(Touchable.disabled);
        button.setColor(Color.BLACK);
    }

    private void enableButton() {
        button.setDisabled(false);
        button.setTouchable(Touchable.enabled);
        button.setColor(Color.WHITE);
        button.setTransform(true);
        button.addAction(Actions.sequence(
                Actions.scaleBy(POPPING_ANIMATION_SCALE, POPPING_ANIMATION_SCALE, 0.1f, Interpolation.fastSlow),
                Actions.scaleBy(-POPPING_ANIMATION_SCALE, -POPPING_ANIMATION_SCALE, 0.1f, Interpolation.linear)
        ));
    }

    @Override
    public Actor build() {
        return button;
    }

    public void setCastTimeCoefficient(int coefficient) {
        this.castTimeCoefficient = coefficient;
    }
}
