package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.entity.battle.DungeonBattleBuilder;
import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.entity.enemy.DungeonEnemyFactory;
import com.mana_wars.model.entity.user.UserDungeonsAPI;
import com.mana_wars.model.interactor.DungeonsInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.DungeonsPresenter;
import com.mana_wars.presentation.view.DungeonsView;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.DungeonButtonsTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mana_wars.model.GameConstants.CHOSEN_BATTLE_BUILDER;

public class DungeonsScreen extends BaseScreen<BaseOverlayUI, DungeonsPresenter> implements DungeonsView {

    private final DungeonButtonsTable dungeonButtonsTable;
    private final AssetFactory<String, Texture> imageFactory;

    public DungeonsScreen(final UserDungeonsAPI user,
                          final ScreenSetter screenSetter,
                          final FactoryStorage factoryStorage,
                          final DatabaseRepository databaseRepository,
                          final BaseOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.MANA_WARS), overlayUI);
        presenter = new DungeonsPresenter(this,
                new DungeonsInteractor(user, databaseRepository),
                Gdx.app::postRunnable);

        dungeonButtonsTable = new DungeonButtonsTable(getSkin(), this::onDungeon);
        imageFactory = factoryStorage.getImageFactory();
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        layer.add(new Image(imageFactory.getAsset("bg2")));

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        layer.add(dungeonButtonsTable);

        return layer;
    }

    @Override
    public void setDungeonsList(List<Dungeon> dungeons) {
        dungeonButtonsTable.setDungeons(dungeons);
    }

    @Override
    public void disableDungeons(int userLevel, boolean insufficientManaAmount) {
        dungeonButtonsTable.disableDungeons(userLevel, insufficientManaAmount);
    }

    @Override
    public void show() {
        super.show();
        presenter.refreshDungeonsList();
        presenter.refreshRequiredManaAmount();
    }

    private void onDungeon(Dungeon dungeon) {
        Map<String, Object> args = new HashMap<>();
        args.put(CHOSEN_BATTLE_BUILDER,
                new DungeonBattleBuilder(presenter.getUser(), new DungeonEnemyFactory(dungeon)));
        setScreen(ScreenInstance.BATTLE, args);
    }

}
