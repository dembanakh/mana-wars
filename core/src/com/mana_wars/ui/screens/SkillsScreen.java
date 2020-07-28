package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.skills_operations.SkillTable;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.ActiveSkill;
import com.mana_wars.model.entity.skills.PassiveSkill;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.user.UserSkillsAPI;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.SkillsPresenter;
import com.mana_wars.presentation.view.SkillsView;
import com.mana_wars.ui.UIElementsSize;
import com.mana_wars.ui.factory.AssetFactory;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.MenuOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.base.TimeoutDragAndDrop;
import com.mana_wars.ui.widgets.skill_window.BriefSkillInfo;
import com.mana_wars.ui.widgets.skills_list_2d.OperationSkillsList;

import java.util.EnumMap;

import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.COLUMNS_NUMBER;

public final class SkillsScreen extends BaseScreen<MenuOverlayUI, SkillsPresenter> implements SkillsView {

    private final OperationSkillsList<Skill> mainSkillsTable;
    private final OperationSkillsList<Skill> activeSkillsTable;
    private final OperationSkillsList<Skill> passiveSkillsTable;
    private final SkillsDragAndDrop dragAndDrop;
    private final ScrollPane scrollPane;
    private final BriefSkillInfo skillInfo;

    private final EnumMap<SkillTable, OperationSkillsList<Skill>> tables = new EnumMap<>(SkillTable.class);

    public SkillsScreen(final UserSkillsAPI user,
                        final Skin skin,
                        final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                        final DatabaseRepository databaseRepository, final MenuOverlayUI overlayUI) {
        super(screenSetter, skin, overlayUI);

        this.presenter = new SkillsPresenter(this,
                new SkillsInteractor(user, databaseRepository), Gdx.app::postRunnable);
        presenter.addObserver_manaAmount(overlayUI.getManaAmountObserver());
        presenter.addObserver_userLevel(overlayUI.getUserLevelObserver());
        presenter.addObserver_userExperience(overlayUI.getUserExperienceObserver());
        presenter.addObserver_userNextLevelRequiredExperienceObserver(overlayUI.getUserNextLevelRequiredExperienceObserver());

        skillInfo = new BriefSkillInfo("", skin);

        mainSkillsTable = UIElementFactory.emptyBackgroundOperationSkillsList(skin, COLUMNS_NUMBER,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                SkillTable.ALL_SKILLS, skillInfo);
        addInputProcessor(mainSkillsTable.getDoubleTapProcessor());

        activeSkillsTable = UIElementFactory.operationSkillsList(skin,
                GameConstants.MAX_CHOSEN_ACTIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                SkillTable.ACTIVE_SKILLS, skillInfo);
        addInputProcessor(activeSkillsTable.getDoubleTapProcessor());

        passiveSkillsTable = UIElementFactory.operationSkillsList(skin,
                GameConstants.USER_PASSIVE_SKILL_COUNT,
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                SkillTable.PASSIVE_SKILLS, skillInfo);
        addInputProcessor(passiveSkillsTable.getDoubleTapProcessor());

        tables.put(SkillTable.ALL_SKILLS, mainSkillsTable);
        tables.put(SkillTable.ACTIVE_SKILLS, activeSkillsTable);
        tables.put(SkillTable.PASSIVE_SKILLS, passiveSkillsTable);

        scrollPane = new ScrollPane(mainSkillsTable.build(), skin);
        dragAndDrop = new SkillsDragAndDrop(factoryStorage.getSkillIconFactory(),
                factoryStorage.getRarityFrameFactory());
    }

    @Override
    void rebuildStage() {
        super.rebuildStage();
        addActor(skillInfo);
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        return new Table();
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table().top();
        layer.setFillParent(true);

        Table scrollPaneCont = new Table(skin);
        scrollPaneCont.setBackground("list");
        scrollPaneCont.add(scrollPane).expand().fill();

        layer.add(activeSkillsTable.build()).top().padTop(UIElementsSize.MENU_OVERLAY_UI.USER_LEVEL_FIELD_HEIGHT)
                .expandX().fillX().row();
        layer.add(passiveSkillsTable.build()).top()
                .expandX().fillX().row();
        layer.add(scrollPaneCont).top().padBottom(UIElementsSize.NAVIGATION_BAR.TAB_HEIGHT)
                .expandY().fillY().expandX().fillX();

        return layer;
    }

    @Override
    public void setSkillsList(Iterable<ActiveSkill> activeSkills,
                              Iterable<PassiveSkill> passiveSkills,
                              Iterable<Skill> skills) {
        mainSkillsTable.setItems(skills);
        activeSkillsTable.setItems(activeSkills);
        passiveSkillsTable.setItems(passiveSkills);
        scrollPane.layout();
    }

    @Override
    public void finishMerge(SkillTable table, int index, Skill skill) {
        OperationSkillsList<Skill> listTarget = getList2D(table);
        listTarget.removeIndex(index);
        listTarget.insert(index, skill);
    }

    @Override
    public void finishSwap(SkillTable tableSource, SkillTable tableTarget,
                           int skillSourceIndex, int skillTargetIndex, Skill skillSource, Skill skillTarget) {
        OperationSkillsList<Skill> listSource = getList2D(tableSource);
        OperationSkillsList<Skill> listTarget = getList2D(tableTarget);
        listTarget.removeIndex(skillTargetIndex);
        listTarget.insert(skillTargetIndex, skillSource);
        listSource.insert(skillSourceIndex, skillTarget);
    }

    @Override
    public void finishMove(SkillTable tableTarget, int skillTargetIndex, Skill skillSource) {
        OperationSkillsList<Skill> listTarget = getList2D(tableTarget);
        listTarget.finishMoveOperation(skillTargetIndex, skillSource);
    }

    @Override
    public void selectSkills(SkillTable table, Iterable<? extends Integer> mergeableIndices) {
        getList2D(table).setSelectedIndices(mergeableIndices);
    }

    @Override
    public void clearSelection() {
        mainSkillsTable.clearSelection();
        activeSkillsTable.clearSelection();
        passiveSkillsTable.clearSelection();
    }

    private OperationSkillsList<Skill> getList2D(SkillTable table) {
        return tables.get(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        dragAndDrop.dragAndDrop.update(delta);
    }

    @Override
    public void show() {
        super.show();
        dragAndDrop.setup();
        presenter.refreshSkillsList();
    }

    private class SkillsDragAndDrop {

        private final TimeoutDragAndDrop dragAndDrop;
        private final AssetFactory<Integer, TextureRegion> skillIconFactory;
        private final AssetFactory<Rarity, TextureRegion> skillFrameFactory;

        SkillsDragAndDrop(AssetFactory<Integer, TextureRegion> skillIconFactory,
                          AssetFactory<Rarity, TextureRegion> skillFrameFactory) {
            this.dragAndDrop = new TimeoutDragAndDrop(0.5f);
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

        private void setupSource(OperationSkillsList<?> table) {
            dragAndDrop.addSource(new TimeoutDragAndDrop.Source(table.build()) {
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

                    EnumMap<SkillTable, Iterable<? extends Skill>> items = new EnumMap<>(SkillTable.class);
                    items.put(SkillTable.ALL_SKILLS, mainSkillsTable.getItems());
                    items.put(SkillTable.ACTIVE_SKILLS, activeSkillsTable.getItems());
                    items.put(SkillTable.PASSIVE_SKILLS, passiveSkillsTable.getItems());
                    new Thread(() -> presenter.onSkillDragStart(skill, table.getTableType(), items)).start();

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
                        table.insert((int) payload.getObject2(), (Skill) payload.getObject1());
                    }
                    presenter.onSkillDragStop();
                }
            });
        }

        private void setupTarget(OperationSkillsList<?> table) {
            dragAndDrop.addTarget(new TimeoutDragAndDrop.Target(table.build()) {
                @Override
                public boolean drag(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload,
                                    float x, float y, int pointer) {
                    Skill from = (Skill) payload.getObject1();
                    Skill to = table.getItemAt(x, y);
                    return presenter.validateOperation(
                            (SkillTable) source.getActor().getUserObject(),
                            (SkillTable) getActor().getUserObject(),
                            from, to);
                }

                @Override
                public void drop(TimeoutDragAndDrop.Source source, TimeoutDragAndDrop.Payload payload,
                                 float x, float y, int pointer) {
                    int fromIndex = (int) payload.getObject2();
                    Skill from = (Skill) payload.getObject1();
                    int toIndex = table.getItemIndexAt(x, y);
                    Skill to = (toIndex == -1) ? null : table.getItem(toIndex);

                    presenter.performOperation(
                            (SkillTable) source.getActor().getUserObject(),
                            (SkillTable) getActor().getUserObject(),
                            from, to, fromIndex, toIndex);
                }
            });
        }

    }

}
