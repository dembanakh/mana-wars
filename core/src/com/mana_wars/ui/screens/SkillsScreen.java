package com.mana_wars.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mana_wars.ManaWars;
import com.mana_wars.model.GameConstants;
import com.mana_wars.model.entity.skills.Skill;
import com.mana_wars.model.entity.skills.SkillFactory;
import com.mana_wars.model.interactor.SkillsInteractor;
import com.mana_wars.presentation.presenters.SkillsPresenter;
import com.mana_wars.presentation.view.SkillsView;
import com.mana_wars.ui.widgets.List2D;

import java.util.List;

public class SkillsScreen extends BaseScreen implements SkillsView {

    private final Stage stage;
    private final Skin skin;

    private final NavigationBar navigationBar;
    private final List2D<Skill> activeSkills;
    private final List2D<Skill> passiveSkills;
    private final List2D<Skill> skillsTable;

    private final SkillsPresenter presenter;

    SkillsScreen() {
        presenter = new SkillsPresenter(this, new SkillsInteractor(
                ManaWars.getInstance().getDatabaseRepository()));

        stage = new Stage();
        skin = ManaWars.getInstance().getScreenManager().getSkinFactory().getAsset("freezing");
        navigationBar = ManaWars.getInstance().getScreenManager().getNavigationBar();
        skillsTable = new List2D<Skill>(skin, 5, ManaWars.getInstance()
                .getScreenManager().getSkillIconFactory()) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item,
                                    float x, float y, float width, float height) {
                TextureRegion texture = textureFactory.getAsset(item.getIconID());
                String text = String.valueOf(item.getLevel());
                float texOffsetX = (width - texture.getRegionWidth()) / 2;
                float texOffsetY = (height - texture.getRegionHeight()) / 2;
                batch.draw(texture, x + texOffsetX, y + texOffsetY);
                font.draw(batch, text, x + width / 2, y + texOffsetY, 0, text.length(),
                        width, alignment, false, "");
            }
        };
        activeSkills = new List2D<Skill>(skin, GameConstants.USER_ACTIVE_SKILL_COUNT, ManaWars.getInstance().getScreenManager().getSkillIconFactory()) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item,
                                    float x, float y, float width, float height) {
                TextureRegion texture = textureFactory.getAsset(item.getIconID());
                String text = String.valueOf(item.getLevel());
                float texOffsetX = (width - texture.getRegionWidth()) / 2;
                float texOffsetY = (height - texture.getRegionHeight()) / 2;
                batch.draw(texture, x + texOffsetX, y + texOffsetY);
                font.draw(batch, text, x + width / 2, y + texOffsetY, 0, text.length(),
                        width, alignment, false, "");
            }
        };
        passiveSkills = new List2D<Skill>(skin, GameConstants.USER_PASSIVE_SKILL_COUNT, ManaWars.getInstance().getScreenManager().getSkillIconFactory()) {
            @Override
            protected void drawItem(Batch batch, BitmapFont font, int index, Skill item,
                                    float x, float y, float width, float height) {
                TextureRegion texture = textureFactory.getAsset(item.getIconID());
                String text = String.valueOf(item.getLevel());
                float texOffsetX = (width - texture.getRegionWidth()) / 2;
                float texOffsetY = (height - texture.getRegionHeight()) / 2;
                batch.draw(texture, x + texOffsetX, y + texOffsetY);
                font.draw(batch, text, x + width / 2, y + texOffsetY, 0, text.length(),
                        width, alignment, false, "");
            }
        };
    }

    private void rebuildStage() {
        // layers
        Table layerBackground = buildBackgroundLayer(skin);
        Table layerForeground = buildForegroundLayer(skin);
        Table layerNavigationBar = navigationBar.rebuild(skin);

        // fill stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setFillParent(true);
        stack.add(layerBackground);
        stack.add(layerForeground);
        stage.addActor(layerNavigationBar);

        setupDragAndDrop();
    }

    private Table buildBackgroundLayer(Skin skin) {
        Table layer = new Table();

        return layer;
    }

    private Table buildForegroundLayer(Skin skin) {
        Table layer = new Table();
        layer.setFillParent(true);

        ScrollPane scrollPane = new ScrollPane(skillsTable, skin);
        skillsTable.setFillParent(true);
        skillsTable.getSelection().setMultiple(false);
        skillsTable.getSelection().setRequired(false);
        activeSkills.getSelection().setMultiple(false);
        activeSkills.getSelection().setRequired(false);
        passiveSkills.getSelection().setMultiple(false);
        passiveSkills.getSelection().setRequired(false);

        //TODO: remove constants
        layer.add(activeSkills).expandX().height(Gdx.graphics.getHeight() * 0.1f).width(Gdx.graphics.getWidth()).row();
        layer.add(passiveSkills).expandX().height(Gdx.graphics.getHeight() * 0.1f).width(Gdx.graphics.getWidth()).row();
        layer.add(scrollPane).expandX().height(Gdx.graphics.getHeight() * 0.7f).width(Gdx.graphics.getWidth()).row();

        return layer;
    }

    private void setupDragAndDrop() {
        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new DragAndDrop.Source(skillsTable) {
            final DragAndDrop.Payload payload = new DragAndDrop.Payload();
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Skill skill = skillsTable.getSelected();
                payload.setObject(skill);
                skillsTable.getItems().removeIndex(skillsTable.getSelectedIndex());
                payload.setDragActor(new Label(skill.getName(), skin));
                return payload;
            }
        });
    }

    @Override
    public void setSkillsList(List<Skill> skills) {
        skillsTable.setItems(skills);
        //TODO: modify active/passive skills table appropriately
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        presenter.refreshSkillsList();
        rebuildStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50.0f/255, 115.0f/255, 220.0f/255, 0.3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}
