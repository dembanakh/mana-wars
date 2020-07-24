package com.mana_wars.ui.storage;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.ManaBonusRepository;
import com.mana_wars.model.repository.DailySkillsRepository;

public interface RepositoryStorage {
    ManaBonusRepository getManaBonusRepository();
    DatabaseRepository getDatabaseRepository();
    DailySkillsRepository getShopRepository();
}
