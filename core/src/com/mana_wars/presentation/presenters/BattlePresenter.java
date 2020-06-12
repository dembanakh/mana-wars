package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.BattleConfig;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.model.interactor.BattleInitializationObserver;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class BattlePresenter extends BasePresenter<BattleInteractor> implements BattleInitializationObserver {

    private BattleView view;

    public BattlePresenter(BattleView view, BattleInteractor interactor, UIThreadHandler uiThreadHandler) {
        super(interactor, uiThreadHandler);
        this.view = view;
    }

    public void addObserver_userHealth(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserHealthObservable().subscribe(observer));
    }

    public void addObserver_enemyHealth(int index, Consumer<? super Integer> observer) {
        disposable.add(interactor.getEnemyHealthObservable(index).subscribe(observer));
    }

    public void initBattle(BattleConfig battle) {
        interactor.init(this, battle);
    }

    public void applyUserSkill(int appliedSkillIndex) {
        //TODO handle empty
        if (interactor.tryApplyUserSkill(appliedSkillIndex)) {
            view.blockSkills(appliedSkillIndex);
        }
    }

    public void updateBattle(float timeDelta) {
        interactor.updateBattle(timeDelta);
        if (interactor.tryFinishBattle()) {
            view.finishBattle();
        }
    }

    public BattleParticipant getPreparedUser() {
        return interactor.getPreparedUser();
    }

    @Override
    public void onStartBattle() {
        view.startBattle();
    }

    @Override
    public void setSkills(Iterable<ActiveSkill> activeSkills, Iterable<PassiveSkill> passiveSkills) {
        uiThreadHandler.postRunnable(() -> view.setSkills(activeSkills, passiveSkills));
    }

    @Override
    public void setOpponents(BattleParticipant user, Iterable<BattleParticipant> userSide, Iterable<BattleParticipant> enemySide) {
        uiThreadHandler.postRunnable(() -> {
            view.setPlayers(user, userSide, enemySide);
        });
    }

}
