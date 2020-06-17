package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ManaBonusProgressBarTest {

    @Test
    public void shouldSynchronizeNow() {
        ManaBonusProgressBar bar = new ManaBonusProgressBar(100, null,
                getProgressBarStyle(), null);

        // assuming SYNC_EVERY_FRAMES > 1

        assertFalse(bar.shouldSynchronizeNow());

        for (int i = 2; i < ManaBonusProgressBar.SYNC_EVERY_FRAMES; ++i) {
            bar.shouldSynchronizeNow();
        }
        assertTrue(bar.shouldSynchronizeNow());
    }

    @Test
    public void update() {
        ManaBonusProgressBar bar = new ManaBonusProgressBar(100, null,
                getProgressBarStyle(), null);

        assertEquals(0, bar.getCurrentValue(), Double.MIN_VALUE);

        bar.update(0.25f * 60);

        assertEquals(0.25, bar.getCurrentValue(), Double.MIN_VALUE);
    }

    @Test
    public void setTimeSinceLastBonusClaim() {
        ManaBonusProgressBar bar = new ManaBonusProgressBar(100, null,
                getProgressBarStyle(), null);
        bar.setTimeSinceLastBonusClaim(1000 * 60 * 5);

        assertEquals(5, bar.getCurrentValue(), Double.MIN_VALUE);
    }

    private ProgressBar.ProgressBarStyle getProgressBarStyle() {
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = new TextureRegionDrawable();
        style.knobBefore = new TextureRegionDrawable();
        return style;
    }

}