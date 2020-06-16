package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.ui.factory.UIElementFactory;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class DungeonButtonsTable extends Table {

    private final Consumer<? super Dungeon> onClick;

    public DungeonButtonsTable(Skin skin, Consumer<? super Dungeon> onClick) {
        super(skin);
        this.onClick = onClick;
    }

    public void setDungeons(List<Dungeon> dungeons) {
        clear();
        float buttonWidth = (float)SCREEN_WIDTH() / dungeons.size();
        for (Dungeon dungeon : dungeons) {
            add(UIElementFactory.getButton(getSkin(), dungeon.getName(), new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        onClick.accept(dungeon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            })).width(buttonWidth);
        }
    }

}
