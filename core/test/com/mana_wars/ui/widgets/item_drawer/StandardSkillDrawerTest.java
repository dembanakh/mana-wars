package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.ReadableSkill;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.base.ListItemDrawer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StandardSkillDrawerTest {

    @Mock
    private AssetFactory<Integer, TextureRegion> iconFactory;
    @Mock
    private AssetFactory<Rarity, TextureRegion> frameFactory;
    @Mock
    private ListItemDrawer<ReadableSkill> itemDrawer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDrawActive() {
        Batch batch = mock(Batch.class);
        BitmapFont font = mock(BitmapFont.class);
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        when(font.getLineHeight()).thenReturn(2f);
        ActiveSkill skill = mock(ActiveSkill.class);
        when(skill.getIconID()).thenReturn(1);
        when(skill.getRarity()).thenReturn(Rarity.EPIC);
        TextureRegion iconRegion = mock(TextureRegion.class);
        TextureRegion frameRegion = mock(TextureRegion.class);
        when(iconFactory.getAsset(1)).thenReturn(iconRegion);
        when(frameFactory.getAsset(Rarity.EPIC)).thenReturn(frameRegion);
        when(iconRegion.getRegionWidth()).thenReturn(20);
        when(iconRegion.getRegionHeight()).thenReturn(20);
        when(frameRegion.getRegionWidth()).thenReturn(40);
        when(frameRegion.getRegionHeight()).thenReturn(40);

        StandardSkillDrawer<ReadableSkill> drawer = new StandardSkillDrawer<>(iconFactory, frameFactory, itemDrawer);
        drawer.draw(batch, font, 0, skill, 0, 0, 100, 100);

        verify(batch).draw(iconRegion, 40, 40);
        verify(batch).draw(frameRegion, 30, 30);

        verify(itemDrawer).draw(batch, font, 0, skill, 40, 40, 20, 20);
    }

}