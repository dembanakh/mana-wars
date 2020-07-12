package com.mana_wars.model.entity.battle.builder;

import com.mana_wars.model.entity.battle.Battle;
import com.mana_wars.model.entity.battle.BattleStateObserver;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.disposables.CompositeDisposable;

public interface BattleBuilder {
    Battle build(BattleStateObserver observer);
    void fetchData(CompositeDisposable disposable, DatabaseRepository databaseRepository, Runnable callback);
}
