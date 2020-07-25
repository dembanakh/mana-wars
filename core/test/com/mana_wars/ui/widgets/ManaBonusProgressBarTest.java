package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.mana_wars.GdxTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ManaBonusProgressBarTest {

    private ManaBonusProgressBar bar;

    @Before
    public void setup() {
        Skin skin = new Skin();
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = new BaseDrawable();
        style.knobBefore = new BaseDrawable();
        skin.add("default-horizontal", style);
        skin.add("default", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
        bar = new ManaBonusProgressBar(skin, 100, null);
    }

    @Test
    public void shouldSynchronizeNow() {
        // assuming SYNC_EVERY_FRAMES > 1

        assertFalse(bar.shouldSynchronizeNow());

        for (int i = 2; i < ManaBonusProgressBar.SYNC_EVERY_FRAMES; ++i) {
            bar.shouldSynchronizeNow();
        }
        assertTrue(bar.shouldSynchronizeNow());
    }

    @Test
    public void update() {
        assertEquals(0, bar.getCurrentValue(), Double.MIN_VALUE);

        bar.update(0.25f * 60);

        assertEquals(0.25, bar.getCurrentValue(), Double.MIN_VALUE);
    }

    @Test
    public void setTimeSinceLastBonusClaim() {
        bar.setTimeSinceLastBonusClaim(1000 * 60 * 5);

        assertEquals(5, bar.getCurrentValue(), Double.MIN_VALUE);
    }

}