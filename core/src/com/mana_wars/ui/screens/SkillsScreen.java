package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.SkillTable;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserSkillsAPI;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.presentation.presenters.SkillsPresenter;
import com.mana_wars.presentation.view.SkillsView;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.MenuBaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.storage.RepositoryStorage;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.widgets.skills_list_2d.List2D;
import com.mana_wars.ui.widgets.skills_list_2d.OperationSkillsList2D;
import com.mana_wars.ui.widgets.TimeoutDragAndDrop;

import java.util.List;

import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.*;
import static com.mana_wars.ui.UIStringConstants.*;

public class SkillsScreen extends BaseScreen<MenuBaseOverlayUI, SkillsPresenter> implements SkillsView {

    private final List2D<Skill> mainSkillsTable;
    private final List2D<Skill> activeSkillsTable;
    private final List2D<Skill> passiveSkillsTable;
    private final SkillsDragAndDrop dragAndDrop;
    private final ScrollPane scrollPane;

    public SkillsScreen(final UserSkillsAPI user,
                        final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                        final RepositoryStorage repositoryStorage, final MenuBaseOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UI_SKIN.FREEZING), overlayUI);

        this.presenter = new SkillsPresenter(this,
                Gdx.app::postRunnable,
                new SkillsInteractor(user, repositoryStorage.getDatabaseRepository()));
        presenter.addObserver_manaAmount(overlayUI.getManaAmountObserver());
        presenter.addObserver_userLevel(overlayUI.getUserLevelObserver());

        mainSkillsTable = new OperationSkillsList2D(getEmptyBackgroundStyle(), COLUMNS_NUMBER,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(), true);
        mainSkillsTable.setUserObject(SkillTable.ALL_SKILLS);
        activeSkillsTable = new OperationSkillsList2D(getSkin(), GameConstants.USER_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(), false);
        activeSkillsTable.setUserObject(SkillTable.ACTIVE_SKILLS);
        passiveSkillsTable = new OperationSkillsList2D(getSkin(), GameConstants.USER_PASSIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(), false);
        passiveSkillsTable.setUserObject(SkillTable.PASSIVE_SKILLS);
        scrollPane = new ScrollPane(mainSkillsTable, getSkin());
        dragAndDrop = new SkillsDragAndDrop(factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory());
    }

    @Override
    protected void rebuildStage() {
        super.rebuildStage();
        dragAndDrop.setup();
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table().top();

        mainSkillsTable.getSelection().setMultiple(false);
        mainSkillsTable.getSelection().setRequired(false);
        Table scrollPaneCont = new Table(skin);
        scrollPaneCont.setBackground("list");
        scrollPaneCont.add(scrollPane).height(MAIN_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH);

        activeSkillsTable.getSelection().setMultiple(false);
        activeSkillsTable.getSelection().setRequired(false);
        passiveSkillsTable.getSelection().setMultiple(false);
        passiveSkillsTable.getSelection().setRequired(false);

        layer.add(activeSkillsTable).top().expandX().height(ACTIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();
        layer.add(passiveSkillsTable).top().expandX().height(PASSIVE_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH).row();
        layer.add(scrollPaneCont).top().expandX().height(MAIN_SKILLS_TABLE_HEIGHT).width(SKILLS_TABLES_WIDTH);

        return layer;
    }

    @Override
    public void setSkillsList(List<ActiveSkill> activeSkills, List<PassiveSkill> passiveSkills, List<Skill> skills) {
        mainSkillsTable.setItems(skills);
        activeSkillsTable.setItems(activeSkills);
        passiveSkillsTable.setItems(passiveSkills);
        scrollPane.layout();
    }

    @Override
    public void finishMerge(SkillTable table, int index, Skill skill) {
        List2D<Skill> listTarget = getList2D(table);
        listTarget.removeIndex(index);
        index = listTarget.insert(index, skill);
        //listTarget.setSelectedIndex(index);
    }

    @Override
    public void finishSwap(SkillTable tableSource, SkillTable tableTarget,
                           int skillSourceIndex, int skillTargetIndex, Skill skillSource, Skill skillTarget) {
        //System.out.println("SWAP");
        List2D<Skill> listSource = getList2D(tableSource);
        List2D<Skill> listTarget = getList2D(tableTarget);
        listTarget.removeIndex(skillTargetIndex);
        int index = listTarget.insert(skillTargetIndex, skillSource);
        //listTarget.setSelectedIndex(index);
        listSource.insert(skillSourceIndex, skillTarget);
    }

    @Override
    public void finishMove(SkillTable tableTarget, int skillTargetIndex, Skill skillSource) {
        //System.out.println("MOVE");
        List2D<Skill> listTarget = getList2D(tableTarget);
        if (tableTarget == SkillTable.ALL_SKILLS) {
            listTarget.insert(skillTargetIndex, skillSource);
        } else {
            listTarget.setItem(skillTargetIndex, skillSource);
        }
    }

    private List2D<Skill> getList2D(SkillTable table) {
        switch (table) {
            case ALL_SKILLS:
                return mainSkillsTable;
            case ACTIVE_SKILLS:
                return activeSkillsTable;
            case PASSIVE_SKILLS:
                return passiveSkillsTable;
        }
        return mainSkillsTable;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        dragAndDrop.dragAndDrop.update(delta);
    }

    @Override
    public void show() {
        super.show();
        presenter.refreshSkillsList();
    }

    @Override
    public void hide() {
        super.hide();
        activeSkillsTable.setSelectedIndex(-1);
        passiveSkillsTable.setSelectedIndex(-1);
        mainSkillsTable.setSelectedIndex(-1);
    }

    @SuppressWarnings("ConstantConditions")
    private class SkillsDragAndDrop {

        private final TimeoutDragAndDrop dragAndDrop;
        private final AssetFactory<Integer, TextureRegion> skillIconFactory;
        private final AssetFactory<Rarity, TextureRegion> skillFrameFactory;

        SkillsDragAndDrop(AssetFactory<Integer, TextureRegion> skillIconFactory,
                          AssetFactory<Rarity, TextureRegion> skillFrameFactory) {
            this.dragAndDrop = new TimeoutDragAndDrop(1);
            this.skillIconFactory = skillIconFactory;
            this.skillFrameFactory = skillFrameFactory;
        }

        void setup() {
            dragAndDrop.clear();
            setupSources();
            setupTargets();
        }

        private void setupSources() {
            setupSource(mainSkillsTable);
            setupSource(activeSkillsTable);
            setupSource(passiveSkillsTable);
        }

        private void setupTargets() {
            setupTarget(mainSkillsTable);
            setupTarget(activeSkillsTable);
            setupTarget(passiveSkillsTable);
        }

        private void setupSource(List2D<Skill> table) {
            dragAndDrop.addSource(new TimeoutDragAndDrop.Source(table) {
                final TimeoutDragAndDrop.Payload payload = new TimeoutDragAndDrop.Payload();
                @Override
                public TimeoutDragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    int itemIndex = table.getItemIndexAt(x, y);
                    if (itemIndex == -1) return null;

                    Skill skill = table.getItem(itemIndex);
                    if (skill.getRarity() == Rarity.EMPTY) return null;
                    payload.setObject1(skill);
                    payload.setObject2(itemIndex);
                    table.removeIndex(itemIndex);
                    Stack stack = new Stack();
                    stack.add(new Image(skillIconFactory.getAsset(skill.getIconID())));
                    stack.add(new Image(skillFrameFactory.getAsset(skill.getRarity())));
                    payload.setDragActor(stack);
                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer,
                                     TimeoutDragAndDrop.Payload payload, TimeoutDragAndDrop.Target target) {
                    if (target == null) {
                        table.insert((int)payload.getObject2(), (Skill)payload.getObject1());
                        table.getSelection().clear();
                    }
                }
            });
        }

        private void setupTarget(List2D<Skill> table) {
            dragAndDrop.addTarget(new TimeoutDragAndDrop.Target(table) {
                @Override
                public boolean drag(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload,
                                    float x, float y, int pointer) {
                    Skill from = (Skill) payload.getObject1();
                    Skill to = table.getItemAt(x, y);
                    return presenter.validateOperation((SkillTable)source.getActor().getUserObject(),
                                                        (SkillTable)getActor().getUserObject(),
                                                        from, to);
                }

                @Override
                public void drop(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload,
                                 float x, float y, int pointer) {
                    int fromIndex = (int) payload.getObject2();
                    Skill from = (Skill) payload.getObject1();
                    int toIndex = table.getItemIndexAt(x, y);
                    Skill to = (toIndex == -1) ? null : table.getItem(toIndex);

                    presenter.performOperation((SkillTable)source.getActor().getUserObject(),
                                                (SkillTable)getActor().getUserObject(),
                                                from, to, fromIndex, toIndex);
                }
            });
        }

    }

    private com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle getEmptyBackgroundStyle() {
        com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle style =
                new com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle(
                        getSkin().get(com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle.class));
        style.background = new BaseDrawable(style.background);
        return style;
    }

}
