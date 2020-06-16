package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.battle.DungeonBattleBuilder;
import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.entity.enemy.DungeonEnemyFactory;
import com.mana_wars.model.interactor.DungeonsInteractor;
import com.mana_wars.presentation.presenters.DungeonsPresenter;
import com.mana_wars.presentation.view.DungeonsView;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mana_wars.model.GameConstants.CHOSEN_BATTLE_BUILDER;

public class DungeonsScreen extends BaseScreen<BaseOverlayUI, DungeonsPresenter> implements DungeonsView {

    public DungeonsScreen(final User user,
                          final ScreenSetter screenSetter,
                          final FactoryStorage factoryStorage,
                          final RepositoryStorage repositoryStorage,
                          final BaseOverlayUI overlayUI) {

        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.FREEZING), overlayUI);

        presenter = new DungeonsPresenter(this,
                new DungeonsInteractor(repositoryStorage.getDatabaseRepository()),
                Gdx.app::postRunnable);

        //TODO delete temp vars
        this.user = user;
        this.screenSetter = screenSetter;
        dungeonLabel = new Label("temp", getSkin());
        dungeonLabel.setFontScale(2);
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();
        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        TextButton backButton = UIElementFactory.getButton(skin, "GO to dungeon",
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                        //TODO rewrite
                        Map<String, Object> args = new HashMap<>();
                        args.put(CHOSEN_BATTLE_BUILDER, new DungeonBattleBuilder(user, new DungeonEnemyFactory(chosenDungeon)));
                        screenSetter.setScreen(ScreenInstance.BATTLE, args);
                    }
                });

        layer.add(backButton).pad(200, 0, 100, 0).bottom().expand().row();
        layer.add(dungeonLabel).pad(400).row();
        return layer;
    }

    @Override
    public void show() {
        super.show();
        presenter.refreshDungeonsList();
    }

    //TODO delete temp vars
    private Dungeon chosenDungeon;
    private final Label dungeonLabel;
    private final User user;
    private final ScreenSetter screenSetter;

    @Override
    public void setDungeonsList(List<Dungeon> dungeons) {
        chosenDungeon = dungeons.get(0);
        dungeonLabel.setText(chosenDungeon.getName());
    }
}
