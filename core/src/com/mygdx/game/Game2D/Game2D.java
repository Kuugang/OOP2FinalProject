package com.mygdx.game.Game2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Screens.MenuScreen;

public class Game2D extends Game {
    public static SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public MenuScreen menuScreen;
    private ResourceManager resourceManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        resourceManager = new ResourceManager();
        menuScreen = new MenuScreen(this);
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
