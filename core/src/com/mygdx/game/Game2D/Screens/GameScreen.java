package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game2D.Entities.player.Player;
import com.mygdx.game.Game2D.Entities.player.PlayerHUD;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Handlers.InputHandler;
import com.mygdx.game.Game2D.Listeners.GameCollisionListener;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

public class GameScreen extends BaseScreen {
    public Game2D game;
    public static Player player;
    public static OrthographicCamera camera;
    public static TiledMap map;
    static InputHandler inputHandler = new InputHandler();
    public static World world;
    private final Box2DDebugRenderer debugRenderer;
    public MapManager mapManager;
    private OrthographicCamera hudCamera;
    private PlayerHUD playerHUD;
    public GameScreen(Game2D game) {
        super(game);
        this.game = game;
        world = new World(new Vector2(0, 0), true);
        mapManager = new MapManager(game);
        world.setContactListener(new GameCollisionListener(game, this, mapManager));

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);

        Gdx.input.setInputProcessor(inputHandler);
        player = new Player(game);
        mapManager.dispatchMap("room", new Vector2(11 * ScreenConfig.originalTileSize, 9 * ScreenConfig.originalTileSize), Direction.DOWN);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);
        playerHUD = new PlayerHUD(hudCamera, player, mapManager);
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

        game.getBatch().setProjectionMatrix(camera.combined);
        mapManager.update();
        player.update();

        playerHUD.render(delta);
//        debugRenderer.render(world, camera.combined);
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
