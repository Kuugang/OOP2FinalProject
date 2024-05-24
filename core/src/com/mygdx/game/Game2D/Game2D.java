package com.mygdx.game.Game2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game2D.Manager.InputManager;
import com.mygdx.game.Game2D.Manager.ProfileManager;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Manager.SoundManager;
import com.mygdx.game.Game2D.Network.GameClient;
import com.mygdx.game.Game2D.Screens.GameScreen;
import com.mygdx.game.Game2D.Screens.MenuScreen;

public class Game2D extends Game {
    public static SpriteBatch batch;
    public static ShapeRenderer shapeRenderer;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public static ProfileManager profileManager;
    public static ResourceManager resourceManager;
    public static SoundManager soundManager;
    private GameClient gameClient;
    public static InputManager inputManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        resourceManager = ResourceManager.getInstance();
        profileManager = new ProfileManager();
        soundManager = new SoundManager();
        menuScreen = new MenuScreen(this);
        inputManager = new InputManager();

        this.setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    public GameClient getGameClient(){
        return gameClient;
    }
    public void setGameClient(GameClient gameClient){
        this.gameClient = gameClient;
    }
}
