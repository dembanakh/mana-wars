package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.BattleConfig;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.model.interactor.BattlePresenterCallback;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class BattlePresenter implements BattlePresenterCallback {

    private BattleView view;
    private BattleInteractor interactor;
    private UIThreadHandler uiThreadHandler;
    private CompositeDisposable disposable = new CompositeDisposable();

    public BattlePresenter(BattleView view, BattleInteractor interactor, UIThreadHandler uiThreadHandler) {
        this.view = view;
        this.uiThreadHandler = uiThreadHandler;
        this.interactor = interactor;
    }

    public void addObserver_userHealth(Consumer<? super Integer> observer) {
        disposable.add(interactor.getUserHealthObservable().subscribe(observer));
    }

    public void addObserver_enemyHealth(Consumer<? super Integer> observer) {
        disposable.add(interactor.getEnemyHealthObservable().subscribe(observer));
    }

    public void initBattle(BattleConfig battle) {
        interactor.init(this, battle);
    }

    public void applyUserSkill(ActiveSkill skill) {
        //TODO handle empty
        interactor.applyUserSkill(skill);
    }

    public void dispose() {
        disposable.dispose();
        interactor.dispose();
    }

    public void updateBattle(float timeDelta) {
        interactor.updateBattle(timeDelta);
        if (interactor.tryFinishBattle()) {
            view.finishBattle();
        }
    }

    @Override
    public void startBattle() {
        view.startBattle();
    }

    @Override
    public void setSkills(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills) {
        uiThreadHandler.postRunnable(() -> view.setSkills(activeSkills, passiveSkills));
    }

    @Override
    public void setOpponents(List<BattleParticipant> userSide, List<BattleParticipant> enemySide) {
        uiThreadHandler.postRunnable(()->{
            System.out.println("Data for init user and enemy health, icon etc");
            System.out.println("User health " + userSide.get(0).getCharacteristicValue(Characteristic.HEALTH));
        });
    }
}
