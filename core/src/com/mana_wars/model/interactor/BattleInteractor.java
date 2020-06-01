package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.battle.Battle;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;


import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class BattleInteractor {

    private LocalUserDataRepository localUserDataRepository;
    private DatabaseRepository databaseRepository;

    private Battle battle;

    private CompositeDisposable disposable = new CompositeDisposable();

    public BattleInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
    }


    public void init(BattlePresenterCallback callback, Battle battle, Consumer<? super Integer> userHealthOnChanged,
                     Consumer<? super Integer> enemyHealthOnChanged) {
        this.battle = battle;
        disposable.add(databaseRepository.getUserSkills().subscribe(skills -> {
            List<Skill> passiveSkills = skills.get(SkillTable.PASSIVE_SKILLS);
            List<Skill> activeSkills = skills.get(SkillTable.ACTIVE_SKILLS);
            battle.getUser().setPassiveSkills(passiveSkills);
            //TODO refactor
            callback.setSkills((List<ActiveSkill>)(List<?>)activeSkills, passiveSkills);
            battle.init();
            callback.setOpponents(battle.getUserSide(), battle.getEnemySide());

            disposable.add(battle.getUser().getHealthObservable().subscribe(userHealthOnChanged,
                    Throwable::printStackTrace
            ));

            disposable.add(battle.getEnemySide().get(0).getHealthObservable().subscribe(enemyHealthOnChanged,
                    Throwable::printStackTrace
            ));

            battle.start();
            callback.startBattle();
        }, Throwable::printStackTrace));
    }

    public boolean updateBattle(double timeDelta){
        return battle.update(timeDelta);
    }

    public void dispose() {
        disposable.dispose();
    }

    public void applySkill(ActiveSkill skill) {
        battle.applyActiveSkill(skill, battle.getUser());
    }
}
