package com.mana_wars.ui.widgets.item_drawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.ShopSkill;
import com.mana_wars.model.entity.skills.ReadableSkill;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillShopPriceAndCountDrawerTest {

    @Test
    public void testDraw() {
        Batch batch = mock(Batch.class);
        BitmapFont font = mock(BitmapFont.class);
        BitmapFont.BitmapFontData fontData = mock(BitmapFont.BitmapFontData.class);
        when(font.getData()).thenReturn(fontData);
        when(font.getLineHeight()).thenReturn(2f);
        ShopSkill skill = mock(ShopSkill.class);
        when(skill.getPrice()).thenReturn(2);
        when(skill.instancesLeft()).thenReturn(10);
        when(skill.canBePurchased()).thenReturn(true);
        TextureRegion region = mock(TextureRegion.class);

        SkillShopPriceAndCountDrawer drawer = new SkillShopPriceAndCountDrawer(region);
        drawer.draw(batch, font, 0, skill, 0, 0, 100, 100);

        verify(font).draw(batch, "2", 0, -1, 0, 1, 100,
                Align.center, false, "");
        verify(font).draw(batch, "10", -100, 101, 0, 2, 200,
                Align.center, false, "");
    }

}