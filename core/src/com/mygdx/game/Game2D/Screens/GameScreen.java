package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.player.Player;
import com.mygdx.game.Game2D.Entities.player.PlayerHUD;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Listeners.GameCollisionListener;
import com.mygdx.game.Game2D.World.MapExit;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.World.World.username;

public class GameScreen extends BaseScreen {
    public static Game2D game;
    public static Player player;
    public static OrthographicCamera camera;
    public static TiledMap map;
    public static World world;
    private final Box2DDebugRenderer debugRenderer;
    public static MapManager mapManager;
    private final PlayerHUD PLAYER_HUD;
    private static GameState gameState;

    public GameScreen(Game2D game) {
        super(game);
        GameScreen.game = game;
        world = new World(new Vector2(0, 0), true);
        mapManager = new MapManager();
        world.setContactListener(new GameCollisionListener(game, this, mapManager));

        camera = new OrthographicCamera();
        viewport = new FitViewport(ScreenConfig.screenWidth, ScreenConfig.screenHeight, camera);
        setupViewport(15, 15);

        player = new Player(username, new Vector2(11 * ScreenConfig.originalTileSize, 9 * ScreenConfig.originalTileSize), Entity.Direction.DOWN);

//        mapManager.dispatchMap(new MapExit("ROOM", new Vector2(11 * ScreenConfig.originalTileSize, 9 * ScreenConfig.originalTileSize), Entity.Direction.DOWN));
        mapManager.dispatchMap(new MapExit("GLE202", new Vector2(3 * ScreenConfig.originalTileSize, 2 * ScreenConfig.originalTileSize), Entity.Direction.DOWN));

        OrthographicCamera hudCamera = new OrthographicCamera();
        PLAYER_HUD = new PlayerHUD(hudCamera, player, mapManager);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (gameState == GameState.PAUSED) {
            PLAYER_HUD.render(delta);
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.zoom = 0.5F;

        //TODO update camera in individual maps
        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();
        //

        //PHYSICS
        world.step(1/60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        mapManager.update();
        player.update();

        PLAYER_HUD.render(delta);
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        PLAYER_HUD.resize(width,  height);
        viewport.update(width, height);
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

    private static void setupViewport(int width, int height) {
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio) {
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth/VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        } else {
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight/VIEWPORT.physicalWidth);
        }
    }

    public static class VIEWPORT {
        private static float viewportWidth;
        private static float viewportHeight;
        private static float virtualWidth;
        private static float virtualHeight;
        private static float physicalWidth;
        private static float physicalHeight;
        private static float aspectRatio;
    }

    public enum GameState {
        SAVING,
        LOADING,
        RUNNING,
        PAUSED,
        GAME_OVER
    }
}