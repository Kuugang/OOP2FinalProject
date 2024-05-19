package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.mygdx.game.Game2D.Entities.player.PlayerHUD2;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Listeners.GameCollisionListener;
import com.mygdx.game.Game2D.World.MapExit;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.World.World.username;

public class GameScreen extends BaseScreen implements ApplicationListener {
    public static Game2D game;
    public static Player player;
    public static OrthographicCamera camera;
    public static TiledMap map;
    public static World world;
    private final Box2DDebugRenderer debugRenderer;
    public static MapManager mapManager;
    public static GameState gameState;
    public static PauseScreen pauseScreen;
    public static InputMultiplexer inputMultiplexer;
    private final PlayerHUD2 PLAYER_HUD;

    public GameScreen(Game2D game) {
        super(game);
        GameScreen.game = game;
        inputMultiplexer = new InputMultiplexer();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameCollisionListener(game, this, mapManager));

        camera = new OrthographicCamera();
        viewport = new FitViewport(ScreenConfig.screenWidth, ScreenConfig.screenHeight, camera);
        setupViewport(ScreenConfig.screenWidth, ScreenConfig.screenHeight);

        player = new Player(username, new Vector2(11 * ScreenConfig.originalTileSize,
                9 * ScreenConfig.originalTileSize), Entity.Direction.DOWN);

        mapManager = new MapManager();

        mapManager.dispatchMap(new MapExit("TEST", new Vector2(35 * ScreenConfig.originalTileSize,
                5 * ScreenConfig.originalTileSize), Entity.Direction.UP));

        OrthographicCamera hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);
//        PLAYER_HUD = new PlayerHUD(hudCamera, player, mapManager);
        PLAYER_HUD = new PlayerHUD2(player);
        debugRenderer = new Box2DDebugRenderer();

        //Initialize GameState
        gameState = GameState.RUNNING;

        //PauseScreen
        pauseScreen = new PauseScreen(this, game);

        //Handle multiple input
        //TODO ADD PLAYERHUD input

        inputMultiplexer.addProcessor(pauseScreen);
        inputMultiplexer.addProcessor(pauseScreen.getStage());
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //Hud renders regardless of state
        PLAYER_HUD.render(delta);
        if (gameState == GameState.PAUSED) {

            pauseScreen.getStage().act(delta);
            pauseScreen.getStage().draw();

            return;
        }


        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.zoom = 0.5F;

        //TODO update camera in individual maps
/*
        if(player.y / ScreenConfig.originalTileSize + (ScreenConfig.maxScreenCol / 2F) > mapManager.currentMap.worldHeight ) {
            camera.position.set(player.getX() + player.getWidth() / 2, (mapManager.currentMap.worldHeight * ScreenConfig.originalTileSize) - (ScreenConfig.maxScreenCol / 2F), 0);
        }else if(player.y / ScreenConfig.originalTileSize - (ScreenConfig.maxScreenCol / 2F) > 0 ){
            camera.position.set(player.getX() + player.getWidth() / 2, (ScreenConfig.maxScreenCol * ScreenConfig.originalTileSize) / 2F, 0);
        }*/


        //PHYSICS
        world.step(1/60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        mapManager.update();
        player.update();

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();

        PLAYER_HUD.render(delta);
        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.setWorldHeight(Gdx.graphics.getHeight() * 1.3F);
        viewport.setWorldWidth(Gdx.graphics.getWidth() * 1.3F);
    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private static void setupViewport(int width, int height) {

        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth = width;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight = height;

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


    public static GameState getGameState() {
        return gameState;
    }

    public GameState setGameState(GameState gameState) {
        this.gameState = gameState;

        return gameState;
    }
    public enum GameState {
        SAVING,
        LOADING,
        RUNNING,
        PAUSED,
        GAME_OVER
    }
}