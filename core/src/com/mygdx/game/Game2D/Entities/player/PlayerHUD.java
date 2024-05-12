package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
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

    private static final String INVENTORY_FULL = "Your inventory is full!";

    public PlayerHUD(Camera cameraHUD, Entity entityPlayer, MapManager mapMgr) {
        player = entityPlayer;
        mapManager = mapMgr;
        Viewport viewport = new ScreenViewport(cameraHUD);
        stage = new Stage(viewport);
        stageKeyboardFocus = stage.getKeyboardFocus();

//        observers = new Array<>();

        json = new Json();

        statusUI = new StatusUI();
        statusUI.setVisible(true);
        statusUI.setPosition(0,Gdx.graphics.getHeight() - statusUI.getHeight());
        statusUI.setKeepWithinStage(false);
        statusUI.setMovable(false);

//        inventoryUI = new InventoryUI();
//        inventoryUI.setKeepWithinStage(false);
//        inventoryUI.setMovable(false);
//        inventoryUI.setVisible(false);
//        inventoryUI.setPosition(statusUI.getWidth(), 0);
//
//        conversationUI = new ConversationUI();
//        conversationUI.setMovable(true);
//        conversationUI.setVisible(false);
//        conversationUI.setPosition(stage.getWidth() / 2, 0);
//        conversationUI.setWidth(stage.getWidth() / 2);
//        conversationUI.setHeight(stage.getHeight() / 2);
//
//        notificationUI = new ConversationUI();
//        notificationUI.removeActor(notificationUI.findActor("scrollPane"));
//        notificationUI.getCloseButton().setVisible(false);
//        notificationUI.getCloseButton().setTouchable(Touchable.disabled);
//        notificationUI.setTitle("");
//        notificationUI.setMovable(false);
//        notificationUI.setVisible(false);
//        notificationUI.setPosition(0, 0);
//        notificationUI.setWidth(stage.getWidth());
//        notificationUI.setHeight(stage.getHeight() / 5);
//        notificationUI.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                notificationUI.setVisible(false);
//                statusUI.setVisible(true);
//            }
//        });
//
//        storeInventoryUI = new StoreInventoryUI();
//        storeInventoryUI.setMovable(false);
//        storeInventoryUI.setVisible(false);
//        storeInventoryUI.setPosition(0, 0);
//
//        questUI = new QuestUI();
//        questUI.setMovable(false);
//        questUI.setVisible(false);
//        questUI.setKeepWithinStage(false);
//        questUI.setPosition(0, stage.getHeight() / 2);
//        questUI.setWidth(stage.getWidth());
//        questUI.setHeight(stage.getHeight() / 2);

        stage.addActor(statusUI);

        statusUI.validate();

        //Observers
//        player.registerObserver(this);
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
//            inputMultiplexer.addProcessor(player.getInputProcessor());
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
        stage.getViewport().update(width, height, true);
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
