package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.enemy.Dungeon;
import com.mana_wars.model.entity.user.UserDungeonsAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class DungeonsInteractor extends BaseInteractor {

    private final UserDungeonsAPI user;
    private final DatabaseRepository databaseRepository;

    public DungeonsInteractor(final UserDungeonsAPI user, final DatabaseRepository databaseRepository) {
        this.user = user;
        this.databaseRepository = databaseRepository;
    }


    public Single<List<Dungeon>> getDungeons() {
        return databaseRepository.getDungeons();
    }

    public UserDungeonsAPI getUser() {
        return user;
    }
}
