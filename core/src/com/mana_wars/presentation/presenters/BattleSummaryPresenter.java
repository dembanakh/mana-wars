package com.mana_wars.presentation.presenters;

import com.mana_wars.model.entity.battle.data.BattleSummaryData;
import com.mana_wars.model.interactor.BattleSummaryInteractor;
import com.mana_wars.presentation.util.UIThreadHandler;
import com.mana_wars.presentation.view.BattleSummaryView;

public final class BattleSummaryPresenter extends BasePresenter<BattleSummaryView, BattleSummaryInteractor> {

    public BattleSummaryPresenter(BattleSummaryView view, BattleSummaryInteractor interactor, UIThreadHandler handler) {
        super(view, interactor, handler);
    }

    public void parseSummaryData(BattleSummaryData summaryData) {
        interactor.parseSummaryData(summaryData);
    }
}
