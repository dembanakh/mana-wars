package com.mana_wars.model.entity.battle;

import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BattleBuilder {
    public abstract BattleConfig build();
    public abstract void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback);

    public abstract void setObserver(BattleRoundsObserver observer);
}
