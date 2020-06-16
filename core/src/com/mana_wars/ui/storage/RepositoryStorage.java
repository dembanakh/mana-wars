package com.mana_wars.ui.storage;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;
import com.mana_wars.model.repository.ManaBonusRepository;

public interface RepositoryStorage {

    ManaBonusRepository getManaBonusRepository();
    DatabaseRepository getDatabaseRepository();

}
