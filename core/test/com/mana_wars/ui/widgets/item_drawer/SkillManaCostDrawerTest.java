package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.skills.ReadableSkill;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillManaCostDrawerTest {

    @Test
    public void testDraw() {
        Batch batch = mock(Batch.class);
        BitmapFont font = mock(BitmapFont.class);
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        when(font.getLineHeight()).thenReturn(2f);
        ReadableSkill skill = mock(ReadableSkill.class);
        when(skill.getManaCost()).thenReturn(2);
        TextureRegion region = mock(TextureRegion.class);

        SkillManaCostDrawer drawer = new SkillManaCostDrawer(region);
        drawer.draw(batch, font, 0, skill, 0, 0, 100, 100);

        verify(font).draw(batch, "2", 0, 1, 0, 1, 200,
                Align.center, false, "");
    }

}