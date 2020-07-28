package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillTypeDrawerTest {

    @Test
    public void testDrawActive() {
        Batch batch = mock(Batch.class);
        BitmapFont font = mock(BitmapFont.class);
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        when(font.getLineHeight()).thenReturn(2f);
        ActiveSkill skill = mock(ActiveSkill.class);
        when(skill.isActive()).thenReturn(true);
        TextureRegion region = mock(TextureRegion.class);

        SkillTypeDrawer drawer = new SkillTypeDrawer(region);
        drawer.draw(batch, font, 0, skill, 0, 0, 100, 100);

        verify(font).draw(batch, "A", 0, 101, 0, 1, 200,
                Align.center, false, "");
    }

    @Test
    public void testDrawPassive() {
        Batch batch = mock(Batch.class);
        BitmapFont font = mock(BitmapFont.class);
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        when(font.getLineHeight()).thenReturn(2f);
        PassiveSkill skill = mock(PassiveSkill.class);
        when(skill.isActive()).thenReturn(false);
        TextureRegion region = mock(TextureRegion.class);

        SkillTypeDrawer drawer = new SkillTypeDrawer(region);
        drawer.draw(batch, font, 0, skill, 0, 0, 100, 100);

        verify(font).draw(batch, "P", 0, 101, 0, 1, 200,
                Align.center, false, "");
    }

}