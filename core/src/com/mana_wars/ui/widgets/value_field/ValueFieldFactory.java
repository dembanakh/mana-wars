package com.mana_wars.ui.widgets.value_field;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.screens.BattleScreen;
import com.mana_wars.ui.widgets.value_field.base.TransformApplier;
import com.mana_wars.ui.widgets.value_field.base.ValueField;

public final class ValueFieldFactory {

    public static <T> ValueField<Void, T> textValueField(TransformApplier transformApplier) {
        return new TextValueField<>(transformApplier);
    }

    public static <T> ValueField<Void, T> textValueField(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                                                         TransformApplier transformApplier) {
        return new TextValueField<>(backgroundColor, transformApplier);
    }

    public static ValueField<Integer, Integer> aliveEnemiesValueField(UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
                                                                      TransformApplier transformApplier) {
        return new AliveEnemiesValueField(backgroundColor, transformApplier);
    }

    public static ValueField<BattleScreen.BattleParticipantData, Integer> battleParticipantValueField(
            TransformApplier transformApplier,
            final AssetFactory<Integer, TextureRegion> iconFactory,
            final AssetFactory<Rarity, TextureRegion> frameFactory,
            final AssetFactory<String, Texture> imageFactory,
            float deltaHealthAnimationDistance,
            float deltaHealthAnimationDuration
    ) {
        return new BattleParticipantValueField(transformApplier, iconFactory, frameFactory,
                imageFactory, deltaHealthAnimationDistance, deltaHealthAnimationDuration);
    }

    public static ValueField<BattleScreen.BattleParticipantData, Integer> battleParticipantValueField(
            UIStringConstants.UI_SKIN.BACKGROUND_COLOR backgroundColor,
            TransformApplier transformApplier,
            final AssetFactory<Integer, TextureRegion> iconFactory,
            final AssetFactory<Rarity, TextureRegion> frameFactory,
            final AssetFactory<String, Texture> imageFactory,
            float deltaHealthAnimationDistance,
            float deltaHealthAnimationDuration
    ) {
        return new BattleParticipantValueField(backgroundColor, transformApplier, iconFactory, frameFactory,
                imageFactory, deltaHealthAnimationDistance, deltaHealthAnimationDuration);
    }

}
