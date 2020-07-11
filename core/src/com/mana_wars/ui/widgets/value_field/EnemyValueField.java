package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;
import com.mana_wars.ui.widgets.value_field.base.ValueFieldWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public final class EnemyValueField implements BuildableUI {

    private final List<ValueFieldWrapper<BattleParticipantData, Integer>> enemyFieldWrappers;

    private final ValueField<BattleParticipantData, Integer> enemyField;

    public EnemyValueField(final Skin skin,
                           final TransformApplier transformApplier,
                           final AssetFactory<Integer, TextureRegion> iconFactory,
                           final AssetFactory<Rarity, TextureRegion> frameFactory,
                           final AssetFactory<String, Texture> imageFactory) {
        enemyFieldWrappers = new ArrayList<>();
        enemyField = ValueFieldFactory.battleParticipantValueField(skin, transformApplier,
                iconFactory, frameFactory, imageFactory,
                200, 1);
    }

    public Consumer<? super Integer> addEnemy(BattleParticipantData enemyData) {
        ValueFieldWrapper<BattleParticipantData, Integer> enemyField =
                new ValueFieldWrapper<>();
        enemyFieldWrappers.add(enemyField);
        enemyField.setInitialData(enemyData);
        return enemyField;
    }

    public void clear() {
        enemyFieldWrappers.clear();
    }

    public void setActiveEnemy(int index) {
        enemyFieldWrappers.get(index).setField(enemyField);
    }

    @Override
    public Actor build() {
        return enemyField.build();
    }
}
