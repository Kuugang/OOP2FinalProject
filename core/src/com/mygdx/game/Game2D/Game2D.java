package com.mygdx.game.Game2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Screens.GameScreen;
import com.mygdx.game.Game2D.Screens.MenuScreen;

public class Game2D extends Game {
    public static SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public static ResourceManager resourceManager;
    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        resourceManager = new ResourceManager();
//        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
//        this.setScreen(gameScreen);
        this.setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}
