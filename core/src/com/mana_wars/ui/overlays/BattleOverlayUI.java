package com.mana_wars.ui.overlays;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.layout_constraints.AbsoluteSizeConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteXPositionConstraint;
import com.mana_wars.ui.layout_constraints.AbsoluteYPositionConstraint;
import com.mana_wars.ui.layout_constraints.RelativeHeightConstraint;
import com.mana_wars.ui.layout_constraints.RelativeWidthConstraint;
import com.mana_wars.ui.widgets.base.BuildableUI;
import com.mana_wars.ui.widgets.value_field.BattleParticipantValueField;
import com.mana_wars.ui.widgets.value_field.ManualTransformTextValueField;
import com.mana_wars.ui.widgets.value_field.ManualTransformValueField;
import com.mana_wars.ui.widgets.value_field.ManualTransformValueFieldWithInitialData;
import com.mana_wars.ui.widgets.value_field.ManualTransformValueFieldWithInitialDataWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.ACTIVE_SKILLS_TABLE_HEIGHT;

public class BattleOverlayUI extends BaseOverlayUI {

    private final ManualTransformValueField<Integer> userManaAmount;

    private final ManualTransformValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> userField;
    private final ManualTransformValueFieldWithInitialData<BattleParticipantValueField.Data, Integer> enemyField;

    private final List<ManualTransformValueFieldWithInitialDataWrapper<BattleParticipantValueField.Data, Integer>> enemyFieldWrappers;

    BattleOverlayUI(final AssetFactory<Integer, TextureRegion> iconFactory,
                    final AssetFactory<Rarity, TextureRegion> frameFactory,
                    final AssetFactory<String, Texture> imageFactory) {
        userField = new BattleParticipantValueField(iconFactory, frameFactory, imageFactory, -200, 1);
        enemyField = new BattleParticipantValueField(iconFactory, frameFactory, imageFactory, 200, 1);
        userManaAmount = new ManualTransformTextValueField<>();
        enemyFieldWrappers = new ArrayList<>();
    }

    @Override
    public void init() {
        userField
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new RelativeHeightConstraint(75));
        enemyField
                .setXConstraint(new AbsoluteXPositionConstraint(Align.right, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.top, 0))
                .setWidthConstraint(new RelativeWidthConstraint(50))
                .setHeightConstraint(new RelativeHeightConstraint(75));
        userManaAmount
                .setXConstraint(new AbsoluteXPositionConstraint(Align.left, 0))
                .setYConstraint(new AbsoluteYPositionConstraint(Align.bottom, ACTIVE_SKILLS_TABLE_HEIGHT))
                .setWidthConstraint(new RelativeWidthConstraint())
                .setHeightConstraint(new AbsoluteSizeConstraint(200));
        super.init();
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
        ManualTransformValueFieldWithInitialDataWrapper<BattleParticipantValueField.Data, Integer> enemyField = new ManualTransformValueFieldWithInitialDataWrapper<>();
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
