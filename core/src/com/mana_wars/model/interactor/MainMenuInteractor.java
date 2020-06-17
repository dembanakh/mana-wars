package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.entity.user.UserMenuAPI;
import com.mana_wars.model.mana_bonus.ManaBonus;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.Single;
import io.reactivex.subjects.Subject;

public final class MainMenuInteractor extends BaseInteractor {

    private final UserMenuAPI user;
    private final ManaBonus manaBonus;

    private final DatabaseRepository databaseRepository;

    public MainMenuInteractor(final UserMenuAPI user, DatabaseRepository databaseRepository,
                              ManaBonus manaBonus) {
        this.user = user;
        this.databaseRepository = databaseRepository;
        this.manaBonus = manaBonus;
    }

    public Single<Long> initManaBonus() {
        manaBonus.init();
        return Single.just(manaBonus.getTimeSinceLastClaim());
    }

    //TODO refactor
    public Single<Skill> getNewSkill() {
        return databaseRepository.getSkillsList().map((skills) -> {

            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add(databaseRepository.insertUserSkill(s).subscribe(() -> {
                System.out.println("Skill added");
            }, Throwable::printStackTrace));

            return s;
        });
    }

    public Subject<Integer> getManaAmountObservable() {
        return user.getManaAmountObservable();
    }

    public Subject<Integer> getUserLevelObservable() {
        return user.getUserLevelObservable();
    }

    private void updateManaAmount(int delta) {
        user.updateManaAmount(delta);
    }

    public long getTimeSinceLastManaBonusClaim() {
        return manaBonus.getTimeSinceLastClaim();
    }

    public boolean isBonusAvailable() {
        return manaBonus.isBonusBitAvailable();
    }

    public void claimBonus() {
        updateManaAmount(manaBonus.evalCurrentBonus());
        manaBonus.onBonusClaimed();
    }

    public int getFullManaBonusTimeout() {
        return manaBonus.getFullBonusTimeout();
    }

    public Single<String> getUsername() {
        return Single.just(user.getName());
    }
}
