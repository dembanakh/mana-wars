package com.mana_wars.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.ui.factory.UIElementFactory;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class ManaBonusProgressBar extends ProgressBar {

    private static final float GRANULARITY = 1f / 60;
    static final float SYNC_EVERY_FRAMES = 60;

    private double currentValue = 0;
    private int frameCounter = 0;

    private Button claimButton;

    private Runnable claimCallback;

    /*
     * @param bonusTimeout - bonus is available every bonusTimeout minutes
     */
    public ManaBonusProgressBar(int fullBonusTimeout, Runnable claimCallback, Skin skin) {
        this(fullBonusTimeout, claimCallback, skin.get("default-horizontal", ProgressBarStyle.class),
                skin.get(TextButton.TextButtonStyle.class));
    }

    ManaBonusProgressBar(int fullBonusTimeout, Runnable claimCallback, ProgressBarStyle pbStyle,
                         TextButton.TextButtonStyle tbStyle) {
        super(0f, fullBonusTimeout, GRANULARITY, false, pbStyle);
        getStyle().background.setMinHeight(200);
        getStyle().knobBefore.setMinHeight(200);
        setAnimateDuration(0.25f);

        if (tbStyle != null && Gdx.graphics != null) {
            claimButton = UIElementFactory.getButton(tbStyle, "CLAIM", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    onClaim();
                }
            });
            claimButton.setVisible(false);
        }

        this.claimCallback = claimCallback;
    }

    private void onClaim() {
        claimButton.setVisible(false);
        claimCallback.run();
        currentValue = 0;
    }

    public Actor rebuild(Skin skin) {
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

    public boolean shouldSynchronizeNow() {
        boolean result = frameCounter == 0 && currentValue < getMaxValue();
        frameCounter++;
        frameCounter %= SYNC_EVERY_FRAMES;

        return result;
    }

    public void update(float delta) {
        currentValue += (double) delta / 60;
    }

    public void setTimeSinceLastBonusClaim(long time) {
        this.currentValue = time / (1000.0 * 60);
    }

    public void onBonusReady() {
        claimButton.setVisible(true);
    }

    double getCurrentValue() {
        return currentValue;
    }

}
