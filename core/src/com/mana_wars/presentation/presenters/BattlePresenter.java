package com.mana_wars.presentation.presenters;

import com.badlogic.gdx.Gdx;
import com.mana_wars.model.entity.User;
import com.mana_wars.model.entity.battle.Battle;
import com.mana_wars.model.entity.battle.BattleParticipant;
import com.mana_wars.model.entity.battle.Characteristic;
import com.mana_wars.model.entity.enemy.EnemyFactory;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.BattleInteractor;
import com.mana_wars.model.interactor.BattlePresenterCallback;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

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

    public void initBattle(Battle battle) {
        interactor.init(this, battle);
    }

    public void applySkill(ActiveSkill skill)
    {
        interactor.applySkill(skill);
        view.setLabelText("Skill user " + skill.getName());
    }

    public void dispose() {
        disposable.dispose();
        interactor.dispose();
    }

    public void updateBattle(double timeDelta){
        if(interactor.updateBattle(timeDelta)){
            view.finishBattle();
        }
    }

    @Override
    public void startBattle() {
        view.startBattle();
    }

    @Override
    public void setSkills(List<ActiveSkill> activeSkills, List<Skill> passiveSkills) {
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
