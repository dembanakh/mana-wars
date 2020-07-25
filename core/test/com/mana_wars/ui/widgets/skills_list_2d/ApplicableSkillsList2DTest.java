package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.GdxTestRunner;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.animation.controller.SkillIconAnimationController;
import com.mana_wars.ui.animation.controller.UIAnimationController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class ApplicableSkillsList2DTest {

    private ApplicableSkillsList2D<ActiveSkill> list;

    @Mock
    private UIAnimationController<Integer, SkillIconAnimationController.Type> animationController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Skin skin = new Skin();
        List.ListStyle style = new List.ListStyle();
        style.font = new BitmapFont();
        style.fontColorUnselected = new Color();
        skin.add("default", style);
        skin.add("font", style.font);
        list = new ApplicableSkillsList2D<>(skin, (batch, font, index, item, x, y, width, height) -> {}, 5,
                (item, index) -> {}, animationController, "font");
    }

    @Test
    public void testDraw() {
        Batch batch = mock(Batch.class);
        list.build().draw(batch, 0.5f);

        verify(animationController).initBatch(eq(batch), any());
    }

    @Test
    public void testSetItems() {
        java.util.List<ActiveSkill> skills = Arrays.asList(mock(ActiveSkill.class), mock(ActiveSkill.class));
        when(skills.get(0).getRarity()).thenReturn(Rarity.EPIC);
        when(skills.get(1).getRarity()).thenReturn(Rarity.EMPTY);

        list.setItems(skills);

        verify(animationController).clear();
        verify(animationController).add(1, Collections.emptyList());
    }

    @Test
    public void testBlockSkills() {
        java.util.List<ActiveSkill> skills = Arrays.asList(mock(ActiveSkill.class), mock(ActiveSkill.class));
        when(skills.get(0).getRarity()).thenReturn(Rarity.EPIC);
        when(skills.get(0).getCastTime(1)).thenReturn(10d);
        when(skills.get(0).getCooldown(1)).thenReturn(10d);
        when(skills.get(1).getRarity()).thenReturn(Rarity.COMMON);
        when(skills.get(1).getCastTime(1)).thenReturn(30d);
        when(skills.get(1).getCooldown(1)).thenReturn(100d);

        list.setItems(skills);
        list.setDurationCoefficients(1, 1);
        list.blockSkills(1);

        verify(animationController).add(0, Arrays.asList(
                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_NON_APPLIED, 30d)));
        verify(animationController).add(1, Arrays.asList(
                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.CAST_APPLIED, 30d),
                new UIAnimationController.KeyFrame<>(SkillIconAnimationController.Type.COOLDOWN, 100d)));
    }

}