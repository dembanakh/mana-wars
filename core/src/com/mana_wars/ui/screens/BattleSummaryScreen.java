package com.mana_wars.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.user.UserBattleSummaryAPI;
import com.mana_wars.presentation.presenters.BasePresenter;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

public class BattleSummaryScreen extends BaseScreen<BaseOverlayUI, BasePresenter> {

    public BattleSummaryScreen(final UserBattleSummaryAPI user,
                               final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                               final RepositoryStorage repositoryStorage, final BaseOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.FREEZING), overlayUI);
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();

        layer.add(new Label("BATTLE FINISHED", skin)).padBottom(100).row();

        layer.add(new Label("INFO 1", skin)).row();
        layer.add(new Label("INFO 2", skin)).row();
        layer.add(new Label("INFO 3", skin)).row();

        layer.add(UIElementFactory.getButton(skin, "TO MAIN MENU", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onMain();
            }
        })).padTop(100);

        return layer;
    }

    private void onMain() {
        setScreen(ScreenInstance.MAIN_MENU, null);
    }

}
