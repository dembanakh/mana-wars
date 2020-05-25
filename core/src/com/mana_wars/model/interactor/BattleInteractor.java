package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.battle.Battle;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.PvEBattle;
import com.mana_wars.model.entity.enemy.EnemyFactory;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.model.repository.LocalUserDataRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BattleInteractor {

    private LocalUserDataRepository localUserDataRepository;
    private DatabaseRepository databaseRepository;

    private Battle battle;

    private CompositeDisposable disposable = new CompositeDisposable();

    public BattleInteractor(LocalUserDataRepository ludr, DatabaseRepository databaseRepository){
        this.localUserDataRepository = ludr;
        this.databaseRepository = databaseRepository;
    }


    public void init(BattlePresenterCallback callback, Battle battle) {
        this.battle = battle;
        disposable.add(databaseRepository.getUserSkills().subscribe(skills -> {
            List<Skill> passiveSkills = skills.get(SkillTable.PASSIVE_SKILLS);
            List<Skill> activeSkills = skills.get(SkillTable.ACTIVE_SKILLS);
            battle.getUser().setPassive_skills(passiveSkills);
            //TODO refactor
            callback.setSkills((List<ActiveSkill>)(List<?>)activeSkills, passiveSkills);
            battle.init();
            callback.setOpponents(battle.getUserSide(), battle.getEnemySide());
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
