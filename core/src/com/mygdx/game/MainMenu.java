package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Scanner;

public class MainMenu implements Screen {
    private final BattleRoyaleGame game;
    public OrthographicCamera camera;
    private String username;

    public MainMenu(BattleRoyaleGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GREEN);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        username = sc.nextLine();
        this.game.setScreen(new GameScreen(username, game));
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
