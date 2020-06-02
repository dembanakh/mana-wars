package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.mana_bonus.ManaBonus;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class MainMenuInteractor {

    private final LocalUserDataRepository localUserDataRepository;
    private final DatabaseRepository databaseRepository;

    private final Subject<Integer> manaAmountObservable;
    private final Subject<Integer> userLevelObservable;
    private final Subject<String> usernameObservable;

    private final ManaBonus manaBonus;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository,
                              ManaBonus manaBonus) {
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
        this.manaAmountObservable = BehaviorSubject.createDefault(ludr.getUserMana());
        this.userLevelObservable = BehaviorSubject.createDefault(ludr.getUserLevel());
        this.usernameObservable = BehaviorSubject.createDefault(ludr.getUsername());
        this.manaBonus = manaBonus;
    }

    public void initManaBonus() {
        manaBonus.init();
    }

    //TODO refactor
    public Single<Skill> getNewSkill() {
        return databaseRepository.getSkillsList().map((skills)->{

            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add(databaseRepository.insertUserSkill(s).subscribe(()->{
                System.out.println("Skill added");
            },Throwable::printStackTrace));

            return s;
        });
    }

    public Subject<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    public Subject<Integer> getUserLevelObservable() {
        return userLevelObservable;
    }

    public Subject<String> getUsernameObservable() {
        return usernameObservable;
    }

    public void updateManaAmount(int delta) {
        int userMana = localUserDataRepository.getUserMana() + delta;
        localUserDataRepository.setUserMana(userMana);
        manaAmountObservable.onNext(userMana);
    }

    public long getTimeSinceLastManaBonusClaim() {
        return manaBonus.getTimeSinceLastClaim();
    }

    public boolean isBonusAvailable() {
        return manaBonus.isBonusBitAvailable();
    }

    public void claimBonus() {
        manaBonus.onBonusClaimed(this::updateManaAmount);
    }

    public int getFullManaBonusTimeout() {
        return manaBonus.getFullBonusTimeout();
    }

    public void dispose() {
        disposable.dispose();
    }

}
