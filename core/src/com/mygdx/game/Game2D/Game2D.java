package com.mygdx.game.Game2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game2D.Manager.*;
import com.mygdx.game.Game2D.Network.GameClient;
import com.mygdx.game.Game2D.Screens.LoadingScreen;
import com.mygdx.game.Game2D.Screens.MenuScreen;
import com.mygdx.game.Game2D.World.MapManager;

public class Game2D extends Game {
    public static SpriteBatch batch;
    public static ShapeRenderer shapeRenderer;
    public MenuScreen menuScreen;
    public static ProfileManager profileManager;
    public static ResourceManager resourceManager;
    private GameClient gameClient;
    public static InputManager inputManager;
    public static MapManager mapManager;
    public static Screen previousScreen;
    public LoadingScreen loadingScreen;
    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        profileManager = new ProfileManager();
        inputManager = new InputManager();
        resourceManager = ResourceManager.getInstance();
        resourceManager.loadAssets();

//        loadingScreen = new LoadingScreen(this);
//        super.setScreen(loadingScreen);

        mapManager = new MapManager();
        AudioManager.getInstance().playMusic("Celeste Original Soundtrack - 02 - First Steps");
        menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }
    public GameClient getGameClient(){
        return gameClient;
    }

    public void setGameClient(GameClient gameClient){
        this.gameClient = gameClient;
    }

    @Override
    public void setScreen(Screen screen) {
        previousScreen = getScreen();
        super.setScreen(screen);
    }
}
