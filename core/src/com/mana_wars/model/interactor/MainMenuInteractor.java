package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class MainMenuInteractor {

    private LocalUserDataRepository localUserDataRepository;
    private DatabaseRepository databaseRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MainMenuInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
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

    public void dispose(){
        disposable.dispose();
    }
}
