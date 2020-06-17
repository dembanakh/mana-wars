package com.mana_wars.ui.widgets.skills_list_2d;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.ui.factory.AssetFactory;

import io.reactivex.functions.Consumer;

public class ClickableSkillsList2D<T extends Skill> extends StaticSkillsList2D<T> {

    private Consumer<? super Integer> onSkillClick;

    ClickableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                          AssetFactory<Rarity, TextureRegion> frameFactory) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory, (skill) -> {
        });
    }

    public ClickableSkillsList2D(Skin skin, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                 AssetFactory<Rarity, TextureRegion> frameFactory,
                                 Consumer<? super Integer> onSkillClick) {
        this(skin.get(List.ListStyle.class), cols, iconFactory, frameFactory, onSkillClick);
    }

    private ClickableSkillsList2D(List.ListStyle style, int cols, AssetFactory<Integer, TextureRegion> iconFactory,
                                  AssetFactory<Rarity, TextureRegion> frameFactory,
                                  Consumer<? super Integer> onSkillClick) {
        super(style, cols, iconFactory, frameFactory);
        setOnSkillClick(onSkillClick);
    }

    void setOnSkillClick(Consumer<? super Integer> onSkillClick) {
        this.onSkillClick = onSkillClick;
    }

    @Override
    protected void addTouchListeners() {
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return true;
                if (items.size == 0) return true;
                int index = getItemIndexAt(x, y);
                if (index == -1) return true;
                pressedIndex = index;

                if (isClickable(index)) {
                    try {
                        onSkillClick.accept(index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return;
                pressedIndex = -1;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                overIndex = getItemIndexAt(x, y);
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                overIndex = getItemIndexAt(x, y);
                return false;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == 0) pressedIndex = -1;
                if (pointer == -1) overIndex = -1;
            }
        });
    }

    protected boolean isClickable(int index) {
        return getItem(index) != null;
    }

}
