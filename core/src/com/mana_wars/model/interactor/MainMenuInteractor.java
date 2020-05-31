package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.mana_bonus.ManaBonus;
import com.mana_wars.model.mana_bonus.ManaBonusImpl;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class MainMenuInteractor {

    private final LocalUserDataRepository localUserDataRepository;
    private final DatabaseRepository databaseRepository;

    private final PublishSubject<Integer> manaAmountObservable;
    private final PublishSubject<Integer> userLevelObservable;

    private final ManaBonus manaBonus;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository,
                              ManaBonus manaBonus) {
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
        this.manaAmountObservable = PublishSubject.create();
        this.userLevelObservable = PublishSubject.create();
        this.manaBonus = manaBonus;

    }

    public void init() {
        manaBonus.init();
    }

    //TODO refactor
    public Single<Skill> getNewSkill() {
        return databaseRepository.getSkillsList().map((skills)->{

            Skill s = SkillFactory.getNewSkill(skills);
            disposable.add( databaseRepository.insertUserSkill(s).subscribe(()->{
                System.out.println("Skill added");
            },Throwable::printStackTrace));

            return s;
        });
    }

    public PublishSubject<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    public PublishSubject<Integer> getUserLevelObservable() {
        return userLevelObservable;
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
