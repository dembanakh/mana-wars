package com.mana_wars.model.interactor;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.repository.LocalUserDataRepository;

public class MainMenuInteractor {

    private LocalUserDataRepository localUserDataRepository;

    public MainMenuInteractor(LocalUserDataRepository ludr){
        this.localUserDataRepository = ludr;
    }

    //TODO save skill
    public Skill getNewSkill() {
        return SkillFactory.getNewSkill();
    }
}
