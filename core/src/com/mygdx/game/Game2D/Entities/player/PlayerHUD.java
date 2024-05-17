package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.status.StatusObserver;
import com.mygdx.game.Game2D.status.StatusUI;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

public class PlayerHUD implements Screen, StatusObserver {

    private Stage stage;
    private Entity player;

    private StatusUI statusUI;
    private Dialog messageBoxUI;
    private Json json;
    private MapManager mapManager;

    private Actor stageKeyboardFocus;
    OrthographicCamera cameraHUD;
    Viewport viewport;
    public PlayerHUD(OrthographicCamera cameraHUD, Entity entityPlayer, MapManager mapMgr) {
        player = entityPlayer;
        mapManager = mapMgr;
        this.cameraHUD = cameraHUD;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cameraHUD);
        cameraHUD.setToOrtho(false, viewport.getScreenWidth(), viewport.getScreenHeight());
        stage = new Stage(viewport);
        stageKeyboardFocus = stage.getKeyboardFocus();

        json = new Json();

        statusUI = new StatusUI();
        statusUI.setVisible(true);
        statusUI.setPosition(0, Gdx.graphics.getHeight() - statusUI.getHeight());
        statusUI.setKeepWithinStage(false);
        statusUI.setMovable(false);

        stage.addActor(statusUI);

        statusUI.validate();

        // Observers
        statusUI.addObserver(this);
    }

    public Stage getStage() {
        return stage;
    }

    public StatusUI getStatusUI() {
        return statusUI;
    }

    private void setInputUI(Window ui) {
        if (ui.isVisible()) {
            Gdx.input.setInputProcessor(stage);
        } else {
            stage.setKeyboardFocus(stageKeyboardFocus);
            InputMultiplexer inputMultiplexer = new InputMultiplexer();
            inputMultiplexer.addProcessor(stage);
            Gdx.input.setInputProcessor(inputMultiplexer);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.setScreenWidth(Gdx.graphics.getWidth());
        viewport.setScreenHeight(Gdx.graphics.getHeight());
        cameraHUD.setToOrtho(false, viewport.getScreenWidth(), viewport.getScreenHeight());

        stage.getViewport().update(viewport.getScreenWidth(),viewport.getScreenHeight(), true);
        statusUI.setPosition(0,  height - statusUI.getHeight());
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onNotify(int value, StatusEvent event) {

    }
}
