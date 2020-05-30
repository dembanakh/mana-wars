package com.mana_wars.ui.screens;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

public interface RepositoryStorage {

    LocalUserDataRepository getLocalUserDataRepository();
    DatabaseRepository getDatabaseRepository();

}
