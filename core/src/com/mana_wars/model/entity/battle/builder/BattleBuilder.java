package com.mana_wars.model.entity.battle.builder;

import com.mana_wars.model.entity.battle.Battle;
import com.mana_wars.model.entity.battle.BattleStateObserver;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BattleBuilder {
    public abstract Battle build(BattleStateObserver observer);
    public abstract void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback);
}
