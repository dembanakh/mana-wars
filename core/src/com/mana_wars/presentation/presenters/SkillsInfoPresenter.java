package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInfoInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.SkillsInfoView;

import java.util.List;

public final class SkillsInfoPresenter extends BasePresenter<SkillsInfoView, SkillsInfoInteractor> {

    private List<Skill> skillsList;

    public SkillsInfoPresenter(SkillsInfoView view, SkillsInfoInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void fetchSkillsList() {
        disposable.add(interactor.getAllSkills().subscribe(skills -> {
            if (skillsList == null) skillsList = skills;
            uiThreadHandler.postRunnable(() -> view.setSkillsList(skills));
        }, Throwable::printStackTrace));
    }

    public void prepareSkillInfo(int skillIndex) {
        Skill skill = skillsList.get(skillIndex);
        view.openSkillInfoWindow(skill.getIconID(), skill.getName(), skill.getRarity(), skill.getDescription());
    }
    
}
