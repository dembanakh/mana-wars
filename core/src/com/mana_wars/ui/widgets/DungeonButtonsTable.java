package com.mana_wars.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.ui.factory.UIElementFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class DungeonButtonsTable extends Table {

    private final Consumer<? super Dungeon> onClick;

    private final List<DungeonButton> buttons;

    public DungeonButtonsTable(Skin skin, Consumer<? super Dungeon> onClick) {
        super(skin);
        this.onClick = onClick;
        this.buttons = new ArrayList<>();
    }

    public void setDungeons(List<Dungeon> dungeons) {
        clear();
        buttons.clear();

        for (Dungeon dungeon : dungeons) {
            DungeonButton button = new DungeonButton(dungeon, onClick, getSkin());
            buttons.add(button);
            add(button).padBottom(50).row();
        }
    }

    public void disableDungeons(int userLevel, boolean insufficientManaAmount) {
        for (DungeonButton button : buttons) {
            button.setDisabled(userLevel, insufficientManaAmount);
        }
    }

    private static class DungeonButton extends Table {
        private final Label requiredLevel;
        private final TextButton button;

        DungeonButton(Dungeon dungeon, Consumer<? super Dungeon> onClick, Skin skin) {
            super(skin);
            Label name = new Label(dungeon.getName(), skin);
            name.setFontScale(2);
            name.setAlignment(Align.center);
            this.requiredLevel = new Label(Integer.toString(dungeon.getRequiredLvl()), skin);
            this.button = UIElementFactory.getButton(skin, "ENTER", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        onClick.accept(dungeon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            float screenWidth = SCREEN_WIDTH();
            add(name).width(screenWidth / 2);
            add(requiredLevel).width(50);
            add(button).width(screenWidth / 4);
        }

        private void setDisabled(int userLevel, boolean insufficientManaAmount) {
            System.out.println(userLevel + " " + requiredLevel.getText());
            boolean insufficientUserLevel = userLevel < Integer.parseInt(requiredLevel.getText().toString());

            if (insufficientManaAmount || insufficientUserLevel) {
                button.setDisabled(true);
                button.getColor().a = 0.5f;
            }

            if (insufficientUserLevel) {
                requiredLevel.setColor(Color.RED);
            }
        }
    }

}
