package com.mana_wars.ui.overlays;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.widgets.BuildableUI;
import com.mana_wars.ui.widgets.value_field.BattleParticipantValueField;
import com.mana_wars.ui.widgets.value_field.TextValueField;
import com.mana_wars.ui.widgets.value_field.ValueField;
import com.mana_wars.ui.widgets.value_field.ValueFieldWithInitialData;
import com.mana_wars.ui.widgets.value_field.ValueFieldWithInitialDataWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

public class BattleBaseOverlayUI extends BaseOverlayUI {

    private final ValueField<Integer> userManaAmount;

    private final ValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> userField;
    private final ValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> enemyField;

    private final List<ValueFieldWithInitialDataWrapper<BattleParticipantValueField.Data, Integer>> enemyFieldWrappers;

    BattleBaseOverlayUI(final ScreenSetter screenSetter,
                        final AssetFactory<Integer, TextureRegion> iconFactory,
                        final AssetFactory<Rarity, TextureRegion> frameFactory) {
        userField = new BattleParticipantValueField(iconFactory, frameFactory, -200, 1)
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new AbsoluteSizeConstraint(800));
        enemyField = new BattleParticipantValueField(iconFactory, frameFactory, 200, 1)
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new AbsoluteSizeConstraint(800));
        userManaAmount = new TextValueField<Integer>()
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 800))
                .setWidthConstraint(new RelativeWidthConstraint())
                .setHeightConstraint(new AbsoluteSizeConstraint(200));
        enemyFieldWrappers = new ArrayList<>();
    }

    @Override
    protected Iterable<BuildableUI> getElements() {
        return Arrays.asList(userField, enemyField, userManaAmount);
    }

    public void clear() {
        enemyFieldWrappers.clear();
    }

    public Consumer<? super Integer> setUser(String name, int initialHealth,
                                             Iterable<PassiveSkill> passiveSkills) {
        userField.setInitialData(new BattleParticipantValueField.Data(name, initialHealth, passiveSkills));
        return userField;
    }

    public Consumer<? super Integer> addEnemy(String name, int initialHealth,
                                              Iterable<PassiveSkill> passiveSkills) {
        ValueFieldWithInitialDataWrapper<BattleParticipantValueField.Data, Integer> enemyField = new ValueFieldWithInitialDataWrapper<>();
        enemyFieldWrappers.add(enemyField);
        enemyField.setInitialData(new BattleParticipantValueField.Data(name, initialHealth, passiveSkills));
        return enemyField;
    }

    public void setActiveEnemy(int index) {
        enemyFieldWrappers.get(index).setField(enemyField);
    }

    public Consumer<? super Integer> getUserManaAmountObserver() {
        return userManaAmount;
    }

}
