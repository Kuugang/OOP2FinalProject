package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Entities.PlayerMP;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.Network.GameClient;
import com.mygdx.game.Network.GameServer;
import com.mygdx.game.Network.Packets.Packet00Login;
import com.mygdx.game.Network.Packets.Packet02Move;

import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {
    public static Player player;
    OrthographicCamera camera;
    public BattleRoyaleGame game;
    public Texture playerImage;

    public Texture test;
    public static HashMap<String, PlayerMP> otherPlayers; //<username, OtherPlayer Class>

    public GameScreen(String username, BattleRoyaleGame game) {
        player = new Player(username, (double) ScreenConfig.screenWidth / 2,
                (double) ScreenConfig.screenHeight / 2);
        this.game = game;
        Packet00Login packet = new Packet00Login(player.getUsername(), player.getX(), player.getY());
        packet.writeData(BattleRoyaleGame.gameClient);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);

        otherPlayers = new HashMap<>();
        playerImage = new Texture(Gdx.files.internal("drop.png"));
        test = new Texture(Gdx.files.internal("test.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //DRAW
        ScreenUtils.clear(Color.BLUE);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, player.getUsername(), (float) player.x + 50, (float) player.y + 50);
        game.batch.draw(playerImage, (float) player.x, (float) player.y,
                player.playerBounds.width, player.playerBounds.height);

//        game.batch.sha
////        game.batch.draw(test, (float) player.x, (float) player.y, player.playerBounds.width, player.playerBounds.height);

        if(!otherPlayers.isEmpty())
            for(Map.Entry<String, PlayerMP> otherPlayerEntry : otherPlayers.entrySet()){
                PlayerMP otherPlayer = otherPlayerEntry.getValue();
                game.font.draw(game.batch, otherPlayer.getUsername(), (float) otherPlayer.x + 50,
                        (float) otherPlayer.y + 50);
                game.batch.draw(playerImage, (float) otherPlayer.x, (float) otherPlayer.y,
                        otherPlayer.playerBounds.width, otherPlayer.playerBounds.height);
            }
        //UPDATE
        game.batch.end();

        player.update();

        System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
    }

    @Override
    public void resize(int width, int height) {
        game.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
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
