package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ProfileManager;

import static com.mygdx.game.Game2D.Game2D.profileManager;
import static com.mygdx.game.Game2D.Manager.ResourceManager.pixel10;
import static com.mygdx.game.Game2D.Screens.GameScreen.player;

public class PauseScreen implements InputProcessor, Screen {
    private Stage stage;
    private static boolean isPaused = false;
    private Skin skin;
    private static GameScreen gameScreen;

    public PauseScreen(GameScreen gameScreen, Game2D game) {
        PauseScreen.gameScreen = gameScreen;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json")); // You need a skin file

        //Change font of the pausecreen
        skin.getFont("default-font").dispose();
        skin.add("default-font",pixel10);


        Label pauseLabel = new Label("Game Paused", skin);
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton saveButton = new TextButton("Save", skin);
        TextButton exitButton = new TextButton("Exit", skin);


        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(pauseLabel).padBottom(20);
        table.row();
        table.add(resumeButton).width(200).height(50).padBottom(20);
        table.row();
        table.add(saveButton).width(200).height(50).padBottom(20);
        table.row();
        table.add(exitButton).width(200).height(50).padBottom(20);


        stage.addActor(table);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Exit game;
                game.switchToMainMenuScreen();

            }
        });
        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                profileManager.saveProfile(player);
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void resumeGame() {
        isPaused = false;
        gameScreen.setGameState(GameScreen.GameState.RUNNING);
    }

    public void show() {
        gameScreen.setGameState(GameScreen.GameState.PAUSED);
        isPaused = true;
    }

    public void hide() {
        isPaused = false;
        gameScreen.setGameState(GameScreen.GameState.RUNNING);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            if (!isPaused) {
                show();
            } else {
                hide();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Stage getStage() {
        return stage;
    }
}
