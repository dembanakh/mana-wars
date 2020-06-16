package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class DungeonsInteractor extends BaseInteractor{

    private final DatabaseRepository databaseRepository;

    public DungeonsInteractor(DatabaseRepository databaseRepository){
        this.databaseRepository = databaseRepository;
    }


    public Single<List<Dungeon>> getDungeons() {
        return databaseRepository.getDungeons();
    }
}
