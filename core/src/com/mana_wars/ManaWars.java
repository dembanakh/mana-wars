package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.entity.user.User;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.DatabaseUpdater;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.ui.management.ScreenHandler;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.storage.RepositoryStorage;

public class ManaWars extends Game implements ScreenHandler, RepositoryStorage {

    private ScreenManager screenManager;

    //platform repos
    private final LocalUserDataRepository localUserDataRepository;
    private final DatabaseRepository databaseRepository;

    private final DatabaseUpdater databaseUpdater;

    public ManaWars(final LocalUserDataRepository localUserDataRepository,
                    final DatabaseRepository databaseRepository,
                    final DatabaseUpdater databaseUpdater) {
        this.localUserDataRepository = localUserDataRepository;
        this.databaseRepository = databaseRepository;
        this.databaseUpdater = databaseUpdater;
    }

    @Override
    public void create() {
        screenManager = new ScreenManager(this,
                new User(localUserDataRepository, localUserDataRepository, localUserDataRepository),
                this, databaseUpdater);
    }

    @Override
    public LocalUserDataRepository getManaBonusRepository() {
        return localUserDataRepository;
    }

    @Override
    public DatabaseRepository getDatabaseRepository() {
        return databaseRepository;
    }

    @Override
    public void dispose() {
        screenManager.dispose();
    }

}
