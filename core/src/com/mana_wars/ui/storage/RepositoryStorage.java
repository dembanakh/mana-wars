package com.mana_wars.ui.storage;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

public interface RepositoryStorage {

    LocalUserDataRepository getLocalUserDataRepository();
    DatabaseRepository getDatabaseRepository();

}
