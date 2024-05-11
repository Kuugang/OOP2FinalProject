package com.mygdx.game.MultiplayerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MultiplayerGame.Entities.Bullet;
import com.mygdx.game.MultiplayerGame.Entities.PlayerMP;
import com.mygdx.game.MultiplayerGame.Entities.Player;
import com.mygdx.game.MultiplayerGame.Network.Packets.Packet00Login;
import com.mygdx.game.ScreenConfig;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.MultiplayerGame.BattleRoyaleGame.batch;

public class GameScreen implements Screen {
    public static Player player;
    OrthographicCamera camera;
    public BattleRoyaleGame game;
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;

    public static HashMap<String, PlayerMP> otherPlayers; //<username, OtherPlayer Class>

    public GameScreen(String username, BattleRoyaleGame game) {
        player = new Player(username, (float) ScreenConfig.screenWidth / 2, (float) ScreenConfig.screenHeight / 2);
        this.game = game;
        Packet00Login packet = new Packet00Login(player.getUsername(), player.getX(), player.getY());
        packet.writeData(BattleRoyaleGame.gameClient);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);

        otherPlayers = new HashMap<>();
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("Game2D/Maps/HOUSE/HIS_HOUSE.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //DRAW
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        renderer.render();

        batch.begin();
        for (Bullet bullet : Player.playersBullets) {
            bullet.draw();
        }
        player.draw();


        if(!otherPlayers.isEmpty())
            for(Map.Entry<String, PlayerMP> otherPlayerEntry : otherPlayers.entrySet()){
                PlayerMP otherPlayer = otherPlayerEntry.getValue();
                BattleRoyaleGame.font.draw(batch, otherPlayer.getUsername(), otherPlayer.x + 50,
                        otherPlayer.y + 50);
                otherPlayer.draw();
            }

        batch.end();


        //UPDATE
        for (Bullet bullet : Player.playersBullets) {
            bullet.update();
        }
        player.update();
    }

    public static Vector2 getMouse(){
        return new Vector2(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()));
    }

    @Override
    public void resize(int width, int height) {
//        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
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
