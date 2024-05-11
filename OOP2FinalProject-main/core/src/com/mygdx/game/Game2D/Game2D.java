package com.mygdx.game.Game2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game2D extends Game {
    public static SpriteBatch batch;
    public static ShapeRenderer shape;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        this.setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
