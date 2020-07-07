package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class ManaBonusProgressBar extends ProgressBar implements BuildableUI {

    private static final float GRANULARITY = 1f / 60;
    static final float SYNC_EVERY_FRAMES = 60;

    private double currentValue = 0;
    private int frameCounter = 0;

    private final Button claimButton;
    private final Stack actor;

    private final Runnable claimCallback;

    /**
     * @param fullBonusTimeout the time for the all bonus bits to be ready for claim
     * @param claimCallback    called when CLAIM button is pressed
     */
    public ManaBonusProgressBar(Skin skin, int fullBonusTimeout, Runnable claimCallback) {
        super(0f, fullBonusTimeout, GRANULARITY, false, skin);
        this.claimCallback = claimCallback;
        this.claimButton = UIElementFactory.getButton(skin, "CLAIM", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClaim();
            }
        });
        this.actor = new Stack();
        init();
    }

    private void init() {
        getStyle().background.setMinHeight(200);
        getStyle().knobBefore.setMinHeight(200);
        setAnimateDuration(0.25f);
        claimButton.setVisible(false);

        Table barCont = new Table();
        barCont.add(this).padTop(600).width(SCREEN_WIDTH());

        Table buttonCont = new Table();
        buttonCont.add(claimButton).padTop(600);

        actor.add(barCont);
        actor.add(buttonCont);
    }

    @Override
    public Actor build() {
        return actor;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setValue((float) currentValue);
    }

    /**
     * @return true if the view has to synchronize timing with the model in this frame
     */
    public boolean shouldSynchronizeNow() {
        frameCounter++;
        boolean result = frameCounter == SYNC_EVERY_FRAMES && currentValue < getMaxValue();
        frameCounter %= SYNC_EVERY_FRAMES;

        return result;
    }

    public void update(float delta) {
        currentValue += (double) delta / 60;
    }

    public void setTimeSinceLastBonusClaim(long time) {
        currentValue = time / (1000.0 * 60);
    }

    public void onBonusReady() {
        claimButton.setVisible(true);
    }

    double getCurrentValue() {
        return currentValue;
    }

    private void onClaim() {
        claimButton.setVisible(false);
        claimCallback.run();
        currentValue = 0;
    }

}
