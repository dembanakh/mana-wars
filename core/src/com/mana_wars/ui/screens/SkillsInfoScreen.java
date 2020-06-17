package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mana_wars.model.entity.base.Rarity;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInfoInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.SkillsInfoPresenter;
import com.mana_wars.presentation.view.SkillsInfoView;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.SkillInfoWindow;
import com.mana_wars.ui.widgets.skills_list_2d.ClickableSkillsList2D;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SKILLS_SCREEN.SKILLS_TABLES_WIDTH;

public class SkillsInfoScreen extends BaseScreen<BaseOverlayUI, SkillsInfoPresenter> implements SkillsInfoView {

    private final ClickableSkillsList2D<Skill> skillsList;

    private final ScrollPane scrollPane;

    private final SkillInfoWindow skillInfoWindow;

    private final SkillsInfoPresenter presenter;

    public SkillsInfoScreen(final ScreenSetter screenSetter, final FactoryStorage factoryStorage,
                            final DatabaseRepository databaseRepository, final BaseOverlayUI overlayUI) {
        super(screenSetter, factoryStorage.getSkinFactory().getAsset(UIStringConstants.UI_SKIN.FREEZING), overlayUI);
        this.presenter = new SkillsInfoPresenter(this, new SkillsInfoInteractor(databaseRepository), Gdx.app::postRunnable);
        this.skillsList = new ClickableSkillsList2D<>(getSkin(), 5, factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                System.out::println);
        this.scrollPane = new ScrollPane(skillsList, getSkin());
        this.skillInfoWindow = new SkillInfoWindow("INFO", getSkin(),
                factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory());
    }

    @Override
    protected void rebuildStage() {
        super.rebuildStage();
        addActor(skillInfoWindow.build(getSkin()));
    }

    @Override
    public void show() {
        super.show();
        presenter.fetchSkillsList();
    }

    @Override
    protected Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    @Override
    protected Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        Table scrollPaneCont = new Table(skin);
        scrollPaneCont.setBackground("list");
        scrollPaneCont.add(scrollPane).height(SCREEN_HEIGHT()).width(SKILLS_TABLES_WIDTH);

        layer.add(skillsList).top().expandX().height(SCREEN_HEIGHT()).width(SKILLS_TABLES_WIDTH);

        return layer;
    }

    @Override
    public void openSkillInfoWindow(int skillID, String skillName, Rarity skillRarity, String description) {
        skillInfoWindow.open(skillID, skillName, skillRarity, description);
    }

    @Override
    public void setSkillsList(Iterable<Skill> skills) {
        skillsList.setItems(skills);
        scrollPane.layout();
    }

}
