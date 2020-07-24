package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.entity.user.UserMenuAPI;
import com.mana_wars.model.mana_bonus.ManaBonus;
import com.mana_wars.model.repository.DatabaseRepository;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class MainMenuInteractor extends BaseInteractor<UserMenuAPI> {

    private final ManaBonus manaBonus;

    private final DatabaseRepository databaseRepository;

    public MainMenuInteractor(final UserMenuAPI user, DatabaseRepository databaseRepository,
                              ManaBonus manaBonus) {
        super(user);
        this.databaseRepository = databaseRepository;
        this.manaBonus = manaBonus;
    }

    public Single<Long> initManaBonus() {
        manaBonus.init();
        return Single.just(manaBonus.getTimeSinceLastClaim());
    }

    public Single<Skill> getNewSkill() {
        return databaseRepository.getSkillsList().map((skills) -> {

            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add(databaseRepository.insertUserSkill(s).subscribe(() -> {
            }, Throwable::printStackTrace));

            return s;
        });
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

    public int getUserSkillCasesNumber() {
        return user.getSkillCasesNumber();
    }

    public int useSkillCase() {
        return user.updateSkillCases(-1);
    }

    public Observable<Integer> getManaAmountObservable() {
        return user.getManaAmountObservable();
    }

    public Observable<Integer> getUserLevelObservable() {
        return user.getLevelObservable();
    }

    public Observable<Integer> getUserExperienceObservable() {
        return user.getExperienceObservable();
    }

    public Observable<Integer> getUserNextLevelRequiredExperienceObservable() {
        return user.getNextLevelRequiredExperienceObservable();
    }
}
