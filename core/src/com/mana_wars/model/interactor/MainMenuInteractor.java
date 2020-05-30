package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
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

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
        manaAmountObservable = PublishSubject.create();
        userLevelObservable = PublishSubject.create();
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

    public void test_updateLevel(int level) {
        userLevelObservable.onNext(level);
    }

    public void test_updateManaAmount(int mana) {
        manaAmountObservable.onNext(mana);
    }

    public PublishSubject<Integer> getManaAmountObservable() {
        return manaAmountObservable;
    }

    public void dispose() {
        disposable.dispose();
    }

    public PublishSubject<Integer> getUserLevelObservable() {
        return userLevelObservable;
    }
}
