package com.mygdx.game.Game2D;

import com.badlogic.gdx.Screen;

import java.util.Scanner;

public class MainMenu implements Screen {
    public Game2D game;
    String username;

    public MainMenu(Game2D game) {
        this.game = game;

//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter your username: ");
//        username = scanner.nextLine();
        username = "test";
//        scanner.close();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.setScreen(new GameScreen(game, username));
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
