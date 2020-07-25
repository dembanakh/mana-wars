package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.dungeon.Dungeon;
import com.mana_wars.model.entity.user.UserDungeonsAPI;
import com.mana_wars.model.repository.DatabaseRepository;

import java.util.List;

import io.reactivex.Single;

public class DungeonsInteractor extends BaseInteractor<UserDungeonsAPI> {

    private final DatabaseRepository databaseRepository;

    public DungeonsInteractor(final UserDungeonsAPI user, final DatabaseRepository databaseRepository) {
        super(user);
        this.databaseRepository = databaseRepository;
    }

    public Single<List<Dungeon>> getDungeons() {
        return databaseRepository.getDungeons();
    }

    public Single<Integer> getRequiredManaAmountForBattle(){
        return databaseRepository.getRequiredManaAmountForBattle();
    }

    public int getUserLevel() {
        return user.getLevel();
    }

    public int getUserManaAmount() {
        return user.getManaAmount();
    }
}
