package com.mana_wars;

import com.badlogic.gdx.Game;
import com.mana_wars.model.entity.user.User;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.ApplicationDataUpdater;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.model.repository.DailySkillsRepository;
import com.mana_wars.ui.management.ScreenHandler;
import com.mana_wars.ui.management.ScreenManager;
import com.mana_wars.ui.storage.RepositoryStorage;

public class ManaWars extends Game implements ScreenHandler, RepositoryStorage {

    private ScreenManager screenManager;

    //platform repos
    private final LocalUserDataRepository localUserDataRepository;
    private final DatabaseRepository databaseRepository;
    private final DailySkillsRepository dailySkillsRepository;

    private final ApplicationDataUpdater applicationDataUpdater;

    public ManaWars(final LocalUserDataRepository localUserDataRepository,
                    final DatabaseRepository databaseRepository,
                    final DailySkillsRepository dailySkillsRepository,
                    final ApplicationDataUpdater applicationDataUpdater) {
        this.localUserDataRepository = localUserDataRepository;
        this.databaseRepository = databaseRepository;
        this.dailySkillsRepository = dailySkillsRepository;
        this.applicationDataUpdater = applicationDataUpdater;
    }

    @Override
    public void create() {
        screenManager = new ScreenManager(this,
                new User(localUserDataRepository, localUserDataRepository, localUserDataRepository, localUserDataRepository),
                this, applicationDataUpdater);
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
    public DailySkillsRepository getShopRepository() {
        return dailySkillsRepository;
    }

    @Override
    public void dispose() {
        screenManager.dispose();
    }

}
