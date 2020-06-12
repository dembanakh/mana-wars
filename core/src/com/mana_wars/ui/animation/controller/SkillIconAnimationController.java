package com.mana_wars.ui.animation.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.ui.animation.shape_animator.LoweringRectangleAnimator;
import com.mana_wars.ui.animation.shape_animator.RotatingSquareSectorAnimator;
import com.mana_wars.ui.animation.shape_animator.ShapeAnimator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SkillIconAnimationController implements UIAnimationController<Integer, SkillIconAnimationController.Type> {

    private final TextureRegion textureRegion;
    private final BitmapFont font;
    private ShapeDrawer shapeDrawer;

    private final TimeoutMap<Integer, KeyFrame<Type>> timeoutMap;
    private final Set<Integer> emptySkills;

    public SkillIconAnimationController(TextureRegion textureRegion, BitmapFont font) {
        this.textureRegion = textureRegion;
        this.font = font;
        this.timeoutMap = new TimeoutHashMap<>();
        this.emptySkills = new HashSet<>();
    }

    @Override
    public void initBatch(Batch batch) {
        shapeDrawer = new ShapeDrawer(batch, textureRegion);
    }

    @Override
    public void clear() {
        timeoutMap.clear();
        emptySkills.clear();
    }

    @Override
    public void add(Integer data, Iterable<KeyFrame<Type>> keyFrames) {
        if (!keyFrames.iterator().hasNext()) {
            emptySkills.add(data);
            return;
        }
        if (timeoutMap.containsKey(data))
            blendAnimations(data, keyFrames);
        else
            timeoutMap.add(data, keyFrames.iterator());
    }

    private void blendAnimations(Integer data, Iterable<KeyFrame<Type>> keyFrames) {
        Iterator<KeyFrame<Type>> newKeyFramesIterator = keyFrames.iterator();
        KeyFrame<Type> offeredKeyFrame = newKeyFramesIterator.next();
        if (Double.compare(timeoutMap.getRemainingTime(data), offeredKeyFrame.getDuration()) < 0) {
            timeoutMap.remove(data);
            timeoutMap.add(data, keyFrames.iterator());
        }
    }

    @Override
    public void update(float delta) {
        timeoutMap.update(delta);
    }

    @Override
    public void animate(Integer index, float x, float y, float width, float height) {
        if (emptySkills.contains(index)) {
            Type.EMPTY.animator.animate(shapeDrawer, x, y, width, height, 1, 1);
        }
        else if (timeoutMap.containsKey(index)) {
            KeyFrame<Type> currentKeyFrame = timeoutMap.get(index);
            double remainingTime = timeoutMap.getRemainingTime(index);
            currentKeyFrame.type.animator.animate(shapeDrawer, x, y, width, height,
                    remainingTime, currentKeyFrame.getDuration());
            font.setColor(Color.BLACK);
            String remainingTimeText = Integer.toString((int)Math.ceil(remainingTime));
            font.draw(shapeDrawer.getBatch(), remainingTimeText,
                    x, y + (height + font.getLineHeight()) / 2, 0,
                    remainingTimeText.length(), width, Align.center, false, "");
        }
    }

    @Override
    public boolean contains(Integer data) {
        return emptySkills.contains(data) || timeoutMap.containsKey(data);
    }

    public enum Type {
        EMPTY(new LoweringRectangleAnimator(new Color(0, 0, 0, 0.8f))),
        CAST_APPLIED(new RotatingSquareSectorAnimator(new Color(1, 0, 0, 0.75f))),
        CAST_NON_APPLIED(new RotatingSquareSectorAnimator(new Color(0, 1, 0, 0.75f))),
        COOLDOWN(new RotatingSquareSectorAnimator(new Color(0, 0, 1, 0.75f)));

        private final ShapeAnimator animator;

        Type(final ShapeAnimator animator) {
            this.animator = animator;
        }
    }

}
