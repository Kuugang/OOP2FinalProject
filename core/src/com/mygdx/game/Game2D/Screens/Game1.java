package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;

import static com.mygdx.game.Game2D.Screens.GameScreen.camera;

public class Game1 extends BaseScreen {
    Game2D game;
    public Game1(Game2D game){
        super(game);
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getShapeRenderer().setProjectionMatrix(camera.combined);
        game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        game.getShapeRenderer().setColor(Color.BLUE);
        game.getShapeRenderer().circle(100, 100, 10);
        game.getShapeRenderer().end();

        if(Gdx.input.isButtonPressed(0)){
            game.setScreen(new TransitionScreenOLD(this, game.gameScreen, game));
        }
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
