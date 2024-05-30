package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;

import static com.mygdx.game.Game2D.Game2D.shapeRenderer;
import static com.mygdx.game.Game2D.Manager.ResourceManager.assetManager;

public class LoadingScreen extends BaseScreen {
    private static final float PROGRESS_BAR_WIDTH = 500;
    private static final float PROGRESS_BAR_HEIGHT = 50f;
    private final Game2D game = (Game2D) Gdx.app.getApplicationListener();

    public LoadingScreen(Game2D game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Game2D.resourceManager.loadAssets();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderProgressBar();

        if (assetManager.update()) {
            game.setScreen(new GameScreen(game));
        }
    }

    private void renderProgressBar() {
        float progress = assetManager.getProgress();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
                (PROGRESS_BAR_WIDTH) / 2f,
                (PROGRESS_BAR_HEIGHT) / 2f,
                PROGRESS_BAR_WIDTH * progress,
                PROGRESS_BAR_HEIGHT
        );
        shapeRenderer.end();
    }

}