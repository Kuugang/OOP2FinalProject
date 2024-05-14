package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.player.Player;
import com.mygdx.game.Game2D.Entities.player.PlayerHUD;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Listeners.GameCollisionListener;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

import static com.mygdx.game.Game2D.Game2D.batch;

public class GameScreen extends BaseScreen {
    public static Game2D game;
    public static Player player;
    public static OrthographicCamera camera;
    public static TiledMap map;
    public static World world;
    private final Box2DDebugRenderer debugRenderer;
    public static MapManager mapManager;
    private final PlayerHUD PLAYER_HUD;
    public GameScreen(Game2D game) {
        super(game);
        GameScreen.game = game;

        world = new World(new Vector2(0, 0), true);
        mapManager = new MapManager();
        world.setContactListener(new GameCollisionListener(game, this, mapManager));

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);

        player = new Player(Game2D.username, new Vector2(11 * ScreenConfig.originalTileSize, 9 * ScreenConfig.originalTileSize), Entity.Direction.DOWN);

        mapManager.dispatchMap("ROOM", new Vector2(11 * ScreenConfig.originalTileSize, 9 * ScreenConfig.originalTileSize), Entity.Direction.DOWN);

        OrthographicCamera hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);
        PLAYER_HUD = new PlayerHUD(hudCamera, player, mapManager);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.zoom = 0.5F;
        //update camera in individual maps
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();
        world.step(1/60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        mapManager.update();
        player.update();

        PLAYER_HUD.render(delta);
        debugRenderer.render(world, camera.combined);
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
