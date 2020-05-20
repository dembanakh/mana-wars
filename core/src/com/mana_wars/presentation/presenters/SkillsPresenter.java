package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.SkillsOperations;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.presentation.view.SkillsView;

import java.util.Arrays;

import io.reactivex.disposables.CompositeDisposable;

public class SkillsPresenter {

    private SkillsView view;
    private SkillsInteractor interactor;

    private CompositeDisposable disposable = new CompositeDisposable();

    public SkillsPresenter(SkillsView view, SkillsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void refreshSkillsList() {
        disposable.add(interactor.getUserSkills().subscribe(skills -> {
            Gdx.app.postRunnable( ()-> view.setSkillsList(
                    Arrays.asList(Skill.Empty, Skill.Empty, Skill.Empty, Skill.Empty, Skill.Empty),
                    Arrays.asList(Skill.Empty, Skill.Empty, Skill.Empty, Skill.Empty, Skill.Empty),
                    skills));
        }, Throwable::printStackTrace));
    }

    private void mergeSkills(Skill toUpdate, Skill toDelete) {
        disposable.add(interactor.mergeSkills(toUpdate, toDelete).subscribe(()->{
            System.out.println("Skills merged");
        }, Throwable::printStackTrace));
    }

    private void swapSkills(SkillsOperations.Table tableSource, SkillsOperations.Table tableTarget,
                            int skillSourceIndex, int skillTargetIndex,
                            Skill skillSource, Skill skillTarget) {
        // TODO: @Artur write
    }

    private void moveSkill(Skill skill, int index) {
        // TODO: @Artur write
        System.out.println(skill + " " + index);
    }

    public boolean validateOperation(SkillsOperations.Table tableSource, SkillsOperations.Table tableTarget,
                                     Skill skillSource, Skill skillTarget) {
        return interactor.validateAnyOperation(tableSource, tableTarget, skillSource, skillTarget);
    }

    public void performOperation(SkillsOperations.Table tableSource, SkillsOperations.Table tableTarget,
                                 Skill skillSource, Skill skillTarget, int skillSourceIndex, int skillTargetIndex) {
        if (interactor.validateOperation(SkillsOperations.MERGE, tableSource, tableTarget,
                skillSource, skillTarget)) {
            skillTarget.upgradeLevel();
            mergeSkills(skillTarget, skillSource);
            view.finishMerge(tableTarget, skillTargetIndex, skillTarget);
            return;
        }
        if (interactor.validateOperation(SkillsOperations.SWAP, tableSource, tableTarget,
                skillSource, skillTarget)) {
            swapSkills(tableSource, tableTarget, skillSourceIndex, skillTargetIndex, skillSource, skillTarget);
            view.finishSwap(tableSource, tableTarget, skillSourceIndex, skillTargetIndex, skillSource, skillTarget);
            return;
        }
        if (interactor.validateOperation(SkillsOperations.MOVE, tableSource, tableTarget,
                skillSource, skillTarget)) {
            moveSkill(skillSource, (tableTarget == SkillsOperations.Table.ALL_SKILLS) ? -1 : skillTargetIndex);
            view.finishMove(tableTarget, skillTargetIndex, skillSource);
        }
    }

    public void dispose(){
        disposable.dispose();
    }
}
