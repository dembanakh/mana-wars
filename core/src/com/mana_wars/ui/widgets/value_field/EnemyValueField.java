package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.widgets.base.BuildableUI;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;
import com.mana_wars.ui.widgets.value_field.base.ValueFieldWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public final class EnemyValueField implements BuildableUI {

    private final List<ValueFieldWrapper<BattleScreen.BattleParticipantData, Integer>> enemyFieldWrappers;

    private final ValueField<BattleScreen.BattleParticipantData, Integer> enemyField;

    public EnemyValueField(final TransformApplier transformApplier,
                           final AssetFactory<Integer, TextureRegion> iconFactory,
                           final AssetFactory<Rarity, TextureRegion> frameFactory,
                           final AssetFactory<String, Texture> imageFactory) {
        enemyFieldWrappers = new ArrayList<>();
        enemyField = ValueFieldFactory.battleParticipantValueField(transformApplier,
                iconFactory, frameFactory, imageFactory,
                200, 1);
    }

    public Consumer<? super Integer> addEnemy(String name, int initialHealth,
                                              Iterable<PassiveSkill> passiveSkills) {
        ValueFieldWrapper<BattleScreen.BattleParticipantData, Integer> enemyField =
                new ValueFieldWrapper<>();
        enemyFieldWrappers.add(enemyField);
        enemyField.setInitialData(new BattleScreen.BattleParticipantData(name, initialHealth, passiveSkills));
        return enemyField;
    }

    public void setActiveEnemy(int index) {
        enemyFieldWrappers.get(index).setField(enemyField);
    }

    @Override
    public void init() {
        enemyFieldWrappers.clear();
    }

    @Override
    public Actor build(Skin skin) {
        return enemyField.build(skin);
    }
}
