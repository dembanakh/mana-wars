package com.mana_wars.ui.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class SkillTimeoutAnimation implements UIAnimation<Integer, SkillTimeoutAnimation.Type> {

    private final TextureRegion textureRegion;
    private ShapeDrawer shapeDrawer;

    private final TimeoutMap<Integer, KeyFrame<Type>> timeoutMap;
    private final Set<Integer> emptySkills;

    public SkillTimeoutAnimation(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        this.timeoutMap = new TimeoutMap<>();
        this.emptySkills = new HashSet<>();
    }

    @Override
    public void initBatch(Batch batch) {
        shapeDrawer = new ShapeDrawer(batch, textureRegion);
    }

    @Override
    public void add(Integer data, Iterable<KeyFrame<Type>> keyFrames) {
        if (!keyFrames.iterator().hasNext()) {
            emptySkills.add(data);
            return;
        }
        if (timeoutMap.contains(data))
            blendAnimations(data, keyFrames);
        else
            timeoutMap.add(data, keyFrames.iterator());
    }

    private void blendAnimations(Integer data, Iterable<KeyFrame<Type>> keyFrames) {
        KeyFrame<Type> currentKeyFrame = timeoutMap.get(data);
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
            shapeDrawer.setColor(0, 0, 0, 0.8f);
            shapeDrawer.filledRectangle(x, y, width, height);
        }
        else if (timeoutMap.contains(index)) {
            KeyFrame<Type> currentKeyFrame = timeoutMap.get(index);
            currentKeyFrame.type.animate(shapeDrawer, x, y, width, height,
                    timeoutMap.getRemainingTime(index), currentKeyFrame.getDuration());
        }
    }

    @Override
    public boolean contains(Integer data) {
        return timeoutMap.contains(data);
    }

    public enum Type {
        CAST_APPLIED {
            @Override
            void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height,
                         double timeLeft, double duration) {
                shapeDrawer.setColor(1, 0, 0, 0.75f);
                shapeDrawer.filledRectangle(x, y, width, (float)(height * (timeLeft / duration)));
            }
        }, CAST_NON_APPLIED {
            @Override
            void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height,
                         double timeLeft, double duration) {
                shapeDrawer.setColor(0, 1, 0, 0.75f);
                shapeDrawer.filledRectangle(x, y, width, (float)(height * (timeLeft / duration)));
            }
        }, COOLDOWN {
            @Override
            void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height,
                         double timeLeft, double duration) {
                shapeDrawer.setColor(0, 0, 1, 0.75f);
                shapeDrawer.filledRectangle(x, y, width, (float)(height * (timeLeft / duration)));
            }
        };

        abstract void animate(ShapeDrawer shapeDrawer, float x, float y, float width, float height,
                              double timeLeft, double duration);
    }

}
