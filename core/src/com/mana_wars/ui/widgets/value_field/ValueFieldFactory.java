package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.battle.participant.BattleParticipantData;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

public final class ValueFieldFactory {

    public static <T> ValueField<Void, T> textValueField(Skin skin, TransformApplier transformApplier) {
        return new TextValueField<>(skin, transformApplier);
    }

    public static <T> ValueField<Void, T> textValueField(Skin skin,
                                                         UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                                                         TransformApplier transformApplier) {
        return new TextValueField<>(skin, backgroundColor, transformApplier);
    }

    public static ValueField<Integer, Integer> aliveEnemiesValueField(Skin skin,
                                                                      UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                                                                      TransformApplier transformApplier) {
        return new AliveEnemiesValueField(skin, backgroundColor, transformApplier);
    }

    public static ValueField<BattleParticipantData, Integer> battleParticipantValueField(
            final Skin skin,
            final TransformApplier transformApplier,
            final AssetFactory<Integer, TextureRegion> iconFactory,
            final AssetFactory<Rarity, TextureRegion> frameFactory,
            final AssetFactory<String, TextureRegion> imageFactory,
            float deltaHealthAnimationDistance,
            float deltaHealthAnimationDuration
    ) {
        return new BattleParticipantValueField(skin, transformApplier, iconFactory, frameFactory,
                imageFactory, deltaHealthAnimationDistance, deltaHealthAnimationDuration);
    }

    public static ValueField<BattleParticipantData, Integer> battleParticipantValueField(
            final Skin skin,
            UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
            final TransformApplier transformApplier,
            final AssetFactory<Integer, TextureRegion> iconFactory,
            final AssetFactory<Rarity, TextureRegion> frameFactory,
            final AssetFactory<String, TextureRegion> imageFactory,
            float deltaHealthAnimationDistance,
            float deltaHealthAnimationDuration
    ) {
        return new BattleParticipantValueField(skin, backgroundColor, transformApplier, iconFactory, frameFactory,
                imageFactory, deltaHealthAnimationDistance, deltaHealthAnimationDuration);
    }

}
