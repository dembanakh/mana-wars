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
import com.mana_wars.ui.widgets.base.BuildableUI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class DungeonButtonsTable extends Table implements BuildableUI {

    private final Skin skin;
    private final Consumer<? super Dungeon> onClick;
    private final List<DungeonButton> buttons;

    public DungeonButtonsTable(Skin skin, Consumer<? super Dungeon> onClick) {
        super(skin);
        this.skin = skin;
        this.onClick = onClick;
        this.buttons = new ArrayList<>();
    }

    public void setDungeons(List<Dungeon> dungeons) {
        clear();
        buttons.clear();

        for (Dungeon dungeon : dungeons) {
            addDungeon(dungeon);
        }
    }

    private void addDungeon(Dungeon dungeon) {
        DungeonButton button = new DungeonButton(skin, dungeon, onClick);
        buttons.add(button);
        add(button).padBottom(50).row();
    }

    public void disableDungeons(int userLevel, boolean insufficientManaAmount) {
        for (DungeonButton button : buttons) {
            button.setDisabled(userLevel, insufficientManaAmount);
        }
    }

    @Override
    public Actor build() {
        return this;
    }

    private static class DungeonButton extends Table {
        private final Label nameLabel;
        private final Label requiredLevel;
        private final TextButton button;

        DungeonButton(Skin skin, Dungeon dungeon, Consumer<? super Dungeon> onClick) {
            super(skin);
            this.nameLabel = new Label(dungeon.getName(), skin);
            this.requiredLevel = new Label(Integer.toString(dungeon.getRequiredLvl()),
                    skin);
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
            init();
        }

        private void init() {
            nameLabel.setFontScale(2);
            nameLabel.setAlignment(Align.center);
            float screenWidth = SCREEN_WIDTH();
            add(nameLabel).width(screenWidth / 2);
            add(requiredLevel).width(50);
            add(button).width(screenWidth / 4);
        }

        private void setDisabled(int userLevel, boolean insufficientManaAmount) {
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
