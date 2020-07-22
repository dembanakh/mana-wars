package com.mana_wars.model.repository;

import java.util.Calendar;

public interface ShopRepository {
    Calendar getLastRefreshTime();
    void updateRefreshTime(Calendar calendar);
}
