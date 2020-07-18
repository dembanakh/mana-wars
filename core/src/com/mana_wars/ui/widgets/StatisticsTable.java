package com.mana_wars.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.battle.data.ReadableBattleStatisticsData;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.widgets.base.BuildableUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;

public class StatisticsTable implements BuildableUI {

    private static final Tab[] TABS = {
            new Tab("Caused damage", (entry) -> entry.data.getCausedDamage()),
            new Tab("Received damage", (entry) -> entry.data.getReceivedDamage()),
            new Tab("Self healing", (entry) -> entry.data.getSelfHealing()),
            new Tab("Received healing", (entry) -> entry.data.getReceivedHealing())
    };

    private final Table table;
    private final Skin skin;

    private final List<Entry> entries = new ArrayList<>();

    private int currentTabIndex = 0;

    public StatisticsTable(Skin skin) {
        this.table = new Table();
        this.skin = skin;
        table.setDebug(false);
    }

    public void clear() {
        entries.clear();
    }

    public void add(String name, ReadableBattleStatisticsData statisticsData) {
        entries.add(new Entry(name, statisticsData));
    }

    @Override
    public Actor build() {
        return table;
    }

    public void showDefault() {
        currentTabIndex = 0;
        showStatisticTab(currentTabIndex);
    }

    private void showStatisticTab(int index) {
        Tab tab = TABS[index];
        table.clear();
        table.row();
        showHeaders(tab.title);
        sortEntries(tab.extractor);
        for (Entry entry : entries) showEntry(entry, tab.extractor);
    }

    private void showHeaders(String title) {
        int screenWidth = SCREEN_WIDTH();
        table.add().width(screenWidth * 4 / 10f).center();
        table.add().width(screenWidth * 1 / 10f).center();
        table.add().width(screenWidth * 4 / 10f).center();
        table.add().width(screenWidth * 1 / 10f).center();
        table.row();
        table.add(new Label("Name", skin)).center();
        table.add(UIElementFactory.getNoBGButton(skin, "<", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTabIndex--;
                if (currentTabIndex == -1) currentTabIndex += TABS.length;
                showStatisticTab(currentTabIndex);
            }
        })).fillX().center();
        table.add(new Label(title, skin)).center();
        table.add(UIElementFactory.getNoBGButton(skin, ">", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTabIndex++;
                currentTabIndex %= TABS.length;
                showStatisticTab(currentTabIndex);
            }
        })).fillX().center();
        table.row();
    }

    private void showEntry(Entry entry, StatisticExtractor extractor) {
        table.add(new Label(entry.name, skin)).center();
        table.add();
        table.add(new Label(String.valueOf(extractor.extract(entry)), skin)).center();
        table.add();
        table.row();
    }

    private void sortEntries(StatisticExtractor extractor) {
        Collections.sort(entries, (e1, e2) -> extractor.extract(e2) - extractor.extract(e1));
    }

    private static class Entry {
        private final String name;
        private final ReadableBattleStatisticsData data;
        private Entry(String name, ReadableBattleStatisticsData data) {
            this.name = name;
            this.data = data;
        }
    }

    @FunctionalInterface
    private interface StatisticExtractor {
        int extract(Entry entry);
    }

    private static class Tab {
        private final String title;
        private final StatisticExtractor extractor;
        private Tab(String title, StatisticExtractor extractor) {
            this.title = title;
            this.extractor = extractor;
        }
    }
}
