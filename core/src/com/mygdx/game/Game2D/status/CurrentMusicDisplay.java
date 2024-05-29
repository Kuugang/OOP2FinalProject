package com.mygdx.game.Game2D.status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Game2D.Manager.ResourceManager;

public class CurrentMusicDisplay {
    private Stage stage;
    private Label musicLabel;
    private int defaultWidth;
    private int defaultHeight;
    private ScrollPane scrollPane;
    private Table scrollTable;

    public CurrentMusicDisplay() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        defaultWidth = 200;
        defaultHeight = 50;

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas/GUI/GUI.atlas"));
        NinePatch ninePatch = new NinePatch(atlas.findRegion("UI_Flat_Button_Large_Lock_01a1"));
        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(ninePatch);

        NinePatch musicNotes = new NinePatch(atlas.findRegion("MusicNotes"));
        NinePatchDrawable musicNotesDrawable = new NinePatchDrawable(musicNotes);

        Table mainTable = new Table();
        mainTable.pad(10);
        mainTable.left();
        mainTable.setBackground(backgroundDrawable);

        Image musicNotesImage = new Image(musicNotesDrawable);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = ResourceManager.pixel10;
        labelStyle.fontColor = Color.BLACK;

        musicLabel = new Label("", labelStyle);
        musicLabel.setFontScale(1.5f);

        scrollTable = new Table();
        scrollTable.add(musicLabel).align(Align.left);

        scrollPane = new ScrollPane(scrollTable);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);

        mainTable.add(musicNotesImage).padRight(10);
        mainTable.add(scrollPane).width(defaultWidth).height(defaultHeight);

        mainTable.setSize(defaultWidth + 50, defaultHeight);
        stage.addActor(mainTable);

        mainTable.setPosition(700, Gdx.graphics.getHeight() - mainTable.getHeight() - 10);
        startScrolling();
    }

    private void startScrolling() {
        Action scrollAction = new Action() {
            @Override
            public boolean act(float delta) {
                float scrollX = scrollPane.getScrollX();
                float maxScrollX = scrollTable.getWidth() - scrollPane.getWidth();

                if (scrollX >= maxScrollX) {
                    scrollPane.setScrollX(0);
                } else {
                    scrollPane.setScrollX(scrollX + 1);
                }

                return false;
            }
        };

        stage.addAction(scrollAction);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void updateMusicLabel(String musicTitle) {
        musicLabel.setText(musicTitle);
        musicLabel.layout();
        scrollTable.setWidth(musicLabel.getWidth());
        scrollPane.layout();
    }
}
