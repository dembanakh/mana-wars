package com.mana_wars.model.repository;

public interface DatabaseUpdater {
    void checkUpdate(Runnable callback, Runnable onError);
}
