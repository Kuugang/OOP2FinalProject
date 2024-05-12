package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.ScreenConfig;


public class TransitionScreenOLD implements Screen {
    private Screen currentScreen;
    private Screen nextScreen;
    private Game2D game;
    private float alpha = 0;
    private boolean fadeDirection = true;
    OrthographicCamera camera = new OrthographicCamera();
    public TransitionScreenOLD(Screen current, Screen next, Game2D game) {
        this.currentScreen = current;
        this.nextScreen = next;

        game.setScreen(next);
        game.setScreen(current);

        this.game = game;
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (fadeDirection) {
            currentScreen.render(Gdx.graphics.getDeltaTime());
        } else {
            nextScreen.render(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

        game.getShapeRenderer().setProjectionMatrix(this.camera.combined);
        game.getShapeRenderer().setColor(0, 0, 0, alpha);

        game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        game.getShapeRenderer().rect(0, 0, 1000000000, 1000000000);
        game.getShapeRenderer().end();

        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if (alpha >= 1) {
            fadeDirection = false;
        } else if (alpha <= 0 && !fadeDirection) {
            game.setScreen(nextScreen);
        }
        alpha += fadeDirection ? 0.04 : -0.04;
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
