package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.screens.util.UIElementsSize.SCREEN_WIDTH;

class ManaBonusProgressBar extends ProgressBar {

    private static final float GRANULARITY = 1f / 60;
    private static final float SYNC_EVERY_FRAMES = 60;

    private double currentValue = 0;
    private int frameCounter = 0;

    private Button claimButton;

    private Runnable claimCallback;

    /*
     * @param bonusTimeout - bonus is available every bonusTimeout minutes
     */
    ManaBonusProgressBar(int fullBonusTimeout, Skin skin, Runnable claimCallback) {
        super(0f, fullBonusTimeout, GRANULARITY, false, skin);
        getStyle().background.setMinHeight(200);
        getStyle().knobBefore.setMinHeight(200);
        setAnimateDuration(0.25f);

        claimButton = UIElementFactory.getButton(skin, "CLAIM", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClaim();
            }
        });
        claimButton.setVisible(false);

        this.claimCallback = claimCallback;
    }

    private void onClaim() {
        claimButton.setVisible(false);
        claimCallback.run();
        currentValue = 0;
    }

    Actor rebuild(Skin skin) {
        Table barCont = new Table();
        barCont.add(this).padTop(600).width(SCREEN_WIDTH);

        Table buttonCont = new Table();
        buttonCont.add(claimButton).padTop(600);

        Stack stack = new Stack();
        stack.add(barCont);
        stack.add(buttonCont);

        return stack;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setValue((float) currentValue);
    }

    boolean shouldSynchronizeNow() {
        boolean result = frameCounter == 0 && currentValue < getMaxValue();
        frameCounter++;
        frameCounter %= SYNC_EVERY_FRAMES;

        return result;
    }

    void update(float delta) {
        currentValue += (double) delta / 60;
    }

    void setTimeSinceLastBonusClaim(long time) {
        this.currentValue = time / (1000.0 * 60);
    }

    void onBonusReady() {
        claimButton.setVisible(true);
    }

}
