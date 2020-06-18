package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.interactor.SkillsInfoInteractor;
import com.mana_wars.model.repository.DatabaseRepository;
import com.mana_wars.presentation.presenters.SkillsInfoPresenter;
import com.mana_wars.presentation.view.SkillsInfoView;
import com.mana_wars.ui.UIStringConstants;
import com.mana_wars.ui.factory.UIElementFactory;
import com.mana_wars.ui.management.ScreenInstance;
import com.mana_wars.ui.management.ScreenSetter;
import com.mana_wars.ui.overlays.BaseOverlayUI;
import com.mana_wars.ui.storage.FactoryStorage;
import com.mana_wars.ui.widgets.SkillInfoWindow;
import com.mana_wars.ui.widgets.skills_list_2d.ClickableSkillsList2D;

import static com.mana_wars.ui.UIElementsSize.SCREEN_HEIGHT;
import static com.mana_wars.ui.UIElementsSize.SCREEN_WIDTH;
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
        this.skillsList = new ClickableSkillsList2D<Skill>(getSkin(), 5, factoryStorage.getSkillIconFactory(), factoryStorage.getRarityFrameFactory(),
                this::openSkillInfoWindow) {
            @Override
            protected boolean shouldShowLevel(Skill item) {
                return false;
            }
        };
        this.scrollPane = new ScrollPane(skillsList, getSkin());
        this.skillInfoWindow = new SkillInfoWindow(UIStringConstants.SKILL_INFO_WINDOW.TITLE, getSkin(),
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

        layer.add(UIElementFactory.getButton(skin, "TO MAIN MENU", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setScreen(ScreenInstance.MAIN_MENU, null);
            }
        })).top().expandX().fillX().row();

        Table scrollPaneCont = new Table(skin);
        scrollPaneCont.setBackground("list");
        scrollPaneCont.add(scrollPane).expand().fill();

        layer.add(scrollPaneCont).top().expand().fill();

        return layer;
    }

    private void openSkillInfoWindow(int skillIndex) {
        skillInfoWindow.open(skillsList.getItem(skillIndex));
    }

    @Override
    public void setSkillsList(Iterable<Skill> skills) {
        skillsList.setItems(skills);
        scrollPane.layout();
    }

}
