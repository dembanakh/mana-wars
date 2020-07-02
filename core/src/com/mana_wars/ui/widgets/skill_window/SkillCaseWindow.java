package com.mana_wars.ui.widgets.skill_window;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.GET_BUTTON_PADDING_BOTTOM;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.GET_BUTTON_PADDING_LEFT;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.GET_BUTTON_PADDING_RIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILL_CASE_WINDOW.GET_BUTTON_PADDING_TOP;

public class SkillCaseWindow extends BaseSkillWindow implements BuildableUI {

    public SkillCaseWindow(String title, Skin skin, AssetFactory<Integer, TextureRegion> iconFactory,
                           AssetFactory<Rarity, TextureRegion> frameFactory) {
        super(title, skin, iconFactory, frameFactory);
    }

    @Override
    public Actor build(Skin skin) {
        super.build(skin);
        add(UIElementFactory.getButton(skin, UIStringConstants.SKILL_CASE_WINDOW.CLOSE_BUTTON_TEXT,
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        hide();
                    }
                })).bottom().pad(GET_BUTTON_PADDING_TOP, GET_BUTTON_PADDING_LEFT,
                GET_BUTTON_PADDING_BOTTOM, GET_BUTTON_PADDING_RIGHT);
        pack();

        return this;
    }

}
