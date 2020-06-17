package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.model.skills_operations.SkillsOperations;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.SkillsView;

import io.reactivex.functions.Consumer;

public final class SkillsPresenter extends BasePresenter<SkillsView, SkillsInteractor> {

    public SkillsPresenter(SkillsView view, UIThreadHandler uiThreadHandler, SkillsInteractor interactor) {
        super(view, interactor, uiThreadHandler);
    }

    public void addObserver_manaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getManaAmountObservable().subscribe(observer));
    }

    public void addObserver_userLevel(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserLevelObservable().subscribe(observer));
    }

    public void refreshSkillsList() {
        disposable.add(interactor.getUserSkills().subscribe(skills -> {
            uiThreadHandler.postRunnable(() -> view.setSkillsList(
                    skills.activeSkills,
                    skills.passiveSkills,
                    skills.allSkills));
        }, Throwable::printStackTrace));
    }

    private void mergeSkills(Skill toUpdate, Skill toDelete) {
        disposable.add(interactor.mergeSkills(toUpdate, toDelete).subscribe(() -> {
            System.out.println("Skills merged");
        }, Throwable::printStackTrace));
    }

    private void swapSkills(Skill skillSource, Skill skillTarget) {
        disposable.add(interactor.swapSkills(skillSource, skillTarget).subscribe(() -> {
            System.out.println("Skills swapped");
        }, Throwable::printStackTrace));
    }

    private void moveSkill(Skill skill, int index) {
        disposable.add(interactor.moveSkill(skill, index).subscribe(() -> {
            System.out.println("Skills moved");
        }, Throwable::printStackTrace));
    }

    public boolean validateOperation(SkillTable tableSource, SkillTable tableTarget,
                                     Skill skillSource, Skill skillTarget) {
        return interactor.validateAnyOperation(tableSource, tableTarget, skillSource, skillTarget);
    }

    public void performOperation(SkillTable tableSource, SkillTable tableTarget,
                                 Skill skillSource, Skill skillTarget, int skillSourceIndex, int skillTargetIndex) {
        System.out.println(tableSource + " " + tableTarget + " " + skillSource + " " + skillTarget);
        if (interactor.validateOperation(SkillsOperations.MERGE, tableSource, tableTarget,
                skillSource, skillTarget)) {
            skillTarget.upgradeLevel();
            mergeSkills(skillTarget, skillSource);
            view.finishMerge(tableTarget, skillTargetIndex, skillTarget);
            return;
        }
        if (interactor.validateOperation(SkillsOperations.SWAP, tableSource, tableTarget,
                skillSource, skillTarget)) {
            swapSkills(skillSource, skillTarget);
            view.finishSwap(tableSource, tableTarget, skillSourceIndex, skillTargetIndex, skillSource, skillTarget);
            return;
        }
        if (interactor.validateOperation(SkillsOperations.MOVE, tableSource, tableTarget,
                skillSource, skillTarget)) {
            moveSkill(skillSource, (tableTarget == SkillTable.ALL_SKILLS) ? -1 : skillTargetIndex);
            view.finishMove(tableTarget, skillTargetIndex, skillSource);
        }
    }
}
