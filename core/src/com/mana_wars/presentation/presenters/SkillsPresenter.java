package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.skills_operations.SkillsOperations;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.presentation.view.SkillsView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SkillsPresenter {

    private final SkillsView view;
    private final SkillsInteractor interactor;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public SkillsPresenter(SkillsView view, SkillsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void initCallbacks(Consumer<? super Integer> manaAmountCallback,
                              Consumer<? super Integer> userLevelCallback) {
        disposable.add(interactor.getManaAmountObservable().subscribe(manaAmountCallback, Throwable::printStackTrace));
        disposable.add(interactor.getUserLevelObservable().subscribe(userLevelCallback, Throwable::printStackTrace));
    }

    public void refreshSkillsList() {
        disposable.add(interactor.getUserSkills().subscribe(skills -> {
            Gdx.app.postRunnable(() -> view.setSkillsList(
                    skills.get(SkillsOperations.Table.ACTIVE_SKILLS),
                    skills.get(SkillsOperations.Table.PASSIVE_SKILLS),
                    skills.get(SkillsOperations.Table.ALL_SKILLS)));
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
            swapSkills(skillSource, skillTarget);
            view.finishSwap(tableSource, tableTarget, skillSourceIndex, skillTargetIndex, skillSource, skillTarget);
            return;
        }
        if (interactor.validateOperation(SkillsOperations.MOVE, tableSource, tableTarget,
                skillSource, skillTarget)) {
            moveSkill(skillSource, (tableTarget == SkillsOperations.Table.ALL_SKILLS) ? -1 : skillTargetIndex);
            view.finishMove(tableTarget, skillTargetIndex, skillSource);
        }
    }

    public void dispose() {
        disposable.dispose();
    }
}
