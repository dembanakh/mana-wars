package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.ui.factory.AssetFactory;

import io.reactivex.functions.Consumer;

public class BattleSkillsList2D extends List2D<ActiveSkill> {

    private final AssetFactory<Integer, TextureRegion> iconFactory;
    private final AssetFactory<Rarity, TextureRegion> frameFactory;

    private Consumer<? super ActiveSkill> onSkillClick;

    public BattleSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                              AssetFactory<Rarity, TextureRegion> frameFactory) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory);
    }

    private BattleSkillsList2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                        AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(style, cols);
        this.iconFactory = iconFactory;
        this.frameFactory = frameFactory;
        this.selection.setDisabled(true);
    }

    public List2D<ActiveSkill> setOnSkillClick(Consumer<? super ActiveSkill> onSkillClick) {
        this.onSkillClick = onSkillClick;
        return this;
    }

    @Override
    protected void addTouchListeners() {
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return true;
                if (items.size == 0) return true;
                int index = getItemIndexAt(x, y);
                if (index == -1) return true;
                pressedIndex = index;

                ActiveSkill skill = getItem(index);
                if (skill != null && onSkillClick != null) {
                    try {
                        onSkillClick.accept(skill);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return;
                pressedIndex = -1;
            }

            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                overIndex = getItemIndexAt(x, y);
            }

            public boolean mouseMoved (InputEvent event, float x, float y) {
                overIndex = getItemIndexAt(x, y);
                return false;
            }

            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == 0) pressedIndex = -1;
                if (pointer == -1) overIndex = -1;
            }
        });
    }

    @Override
    protected void drawItem(Batch batch, BitmapFont font, int index, ActiveSkill item, float x, float y,
                            float width, float height) {
        TextureRegion icon = iconFactory.getAsset(item.getIconID());
        TextureRegion frame = frameFactory.getAsset(item.getRarity());
        String text = String.valueOf(item.getLevel());

        float iconOffsetX = (width - icon.getRegionWidth()) / 2;
        float iconOffsetY = (height - icon.getRegionHeight()) / 2;
        float frameOffsetX = (width - frame.getRegionWidth()) / 2;
        float frameOffsetY = (height - frame.getRegionHeight()) / 2;

        batch.draw(icon, x + iconOffsetX, y + iconOffsetY);
        batch.draw(frame, x + frameOffsetX, y + frameOffsetY);

        if (item.getRarity() != Rarity.EMPTY) {
            font.setColor(Color.BLACK);
            font.getData().setScale(2);
            font.draw(batch, text, x + width / 2, y + frameOffsetY, 0, text.length(),
                    width, alignment, false, "");
        }
    }

}
