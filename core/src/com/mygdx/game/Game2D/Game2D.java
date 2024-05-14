package com.mygdx.game.Game2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Network.GameClient;
import com.mygdx.game.Game2D.Screens.GameScreen;
import com.mygdx.game.Game2D.Screens.MenuScreen;

public class Game2D extends Game {
    public static SpriteBatch batch;
    public static ShapeRenderer shapeRenderer;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public static ResourceManager resourceManager;
    private GameClient gameClient;
    public static boolean isMultiplayer = false;

    //TODO TEMPORARY
    public static String username;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        resourceManager = ResourceManager.getInstance();
//        gameScreen = new GameScreen(this);
        resourceManager = new ResourceManager();
        menuScreen = new MenuScreen(this);
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
