package com.mana_wars.ui.factory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mana_wars.ui.UIStringConstants.REGION_NAME;
import static com.mana_wars.ui.UIStringConstants.SKILLS_ICONS_FILENAME;
import static com.mana_wars.ui.UIStringConstants.TEXTURE_ATLAS_FORMAT;

public class SkillIconFactory extends AssetFactoryBuilder<Integer, TextureRegion, Integer> {

    private final TextureAtlas atlas;

    public SkillIconFactory(int indexMin, int indexMax) {
        super(range(indexMin, indexMax));
        atlas = new TextureAtlas(String.format(TEXTURE_ATLAS_FORMAT, SKILLS_ICONS_FILENAME));
    }

    @Override
    protected Integer key(Integer index) {
        return index;
    }

    @Override
    protected TextureRegion loadAsset(Integer index) {
        return atlas.findRegion(REGION_NAME, index);
    }

    private static Integer[] range(int min, int max) {
        Integer[] range = new Integer[max - min + 1];
        for (int i = 0, j = min; j <= max; i++, j++) {
            range[i] = j;
        }
        return range;
    }
}
