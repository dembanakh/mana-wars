package com.mana_wars.model.entity.battle;

import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseBattleBuilder {

    public abstract BattleConfig build();

    public abstract void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback);
}
