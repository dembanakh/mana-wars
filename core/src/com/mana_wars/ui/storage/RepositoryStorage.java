package com.mana_wars.ui.storage;

import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.ManaBonusRepository;
import com.mana_wars.model.repository.ShopRepository;

public interface RepositoryStorage {
    ManaBonusRepository getManaBonusRepository();
    DatabaseRepository getDatabaseRepository();
    ShopRepository getShopRepository();
}
