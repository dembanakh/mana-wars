package com.mana_wars.presentation.presenters;

import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.model.skills_operations.SkillsOperations;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.SkillsView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import io.reactivex.functions.Consumer;

public final class SkillsPresenter extends BasePresenter<SkillsView, SkillsInteractor> {

    public SkillsPresenter(SkillsView view, SkillsInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(view, interactor, uiThreadHandler);
    }

    public void refreshSkillsList() {
        disposable.add(interactor.getUserSkills().subscribe(skills -> uiThreadHandler
                .postRunnable(() -> view.setSkillsList(
                    skills.activeSkills,
                    skills.passiveSkills,
                    skills.allSkills)), Throwable::printStackTrace));
    }

    private void mergeSkills(Skill toUpdate, Skill toDelete) {
        disposable.add(interactor.mergeSkills(toUpdate, toDelete).subscribe(() -> {
        }, Throwable::printStackTrace));
    }

    private void swapSkills(Skill skillSource, Skill skillTarget) {
        disposable.add(interactor.swapSkills(skillSource, skillTarget).subscribe(() -> {
        }, Throwable::printStackTrace));
    }

    private void moveSkill(Skill skill, int index) {
        disposable.add(interactor.moveSkill(skill, index).subscribe(() -> {
        }, Throwable::printStackTrace));
    }

    public boolean validateOperation(SkillTable tableSource, SkillTable tableTarget,
                                     Skill skillSource, Skill skillTarget) {
        return interactor.validateAnyOperation(tableSource, tableTarget, skillSource, skillTarget);
    }

    public void performOperation(SkillTable tableSource, SkillTable tableTarget,
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
            moveSkill(skillSource, (tableTarget == SkillTable.ALL_SKILLS) ? -1 : skillTargetIndex);
            view.finishMove(tableTarget, skillTargetIndex, skillSource);
        }
    }

    public void onSkillDragStart(Skill sourceSkill, SkillTable sourceTable,
                                 EnumMap<SkillTable, Iterable<? extends Skill>> tables) {
        for (SkillTable table : tables.keySet()) {
            int index = 0;
            List<Integer> mergeableIndices = new ArrayList<>();
            for (Skill possibleSkill : tables.get(table)) {
                if (interactor.validateOperation(SkillsOperations.MERGE, sourceTable, table,
                        sourceSkill, possibleSkill)) {
                    mergeableIndices.add(index);
                }
                index++;
            }
            uiThreadHandler.postRunnable(() -> view.selectSkills(table, mergeableIndices));
        }
    }

    public void onSkillDragStop() {
        view.clearSelection();
    }

    public void addObserver_userExperience(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserExperienceObservable().subscribe(observer));
    }

    public void addObserver_userNextLevelRequiredExperienceObserver(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserNextLevelRequiredExperienceObservable().subscribe(observer));
    }

    public void addObserver_manaAmount(Consumer<? super Integer> observer) {
        disposable.add(interactor.getManaAmountObservable().subscribe(observer));
    }

    public void addObserver_userLevel(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserLevelObservable().subscribe(observer));
    }
}
