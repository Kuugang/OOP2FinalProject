package com.mygdx.game.Game2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game2D.Entities.Player;
import com.mygdx.game.Game2D.Handlers.InputHandler;
import com.mygdx.game.Game2D.Listeners.GameCollisionListener;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.Game2D.World.GameMap;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

import static com.mygdx.game.Game2D.Game2D.batch;

public class GameScreen implements Screen {
    public Game2D game;
    public static Player player;
    public static OrthographicCamera camera;
    public static TiledMap map;
    static InputHandler inputHandler = new InputHandler();
    public static World world;
    private final Box2DDebugRenderer debugRenderer;
    public static MapManager mapManager = new MapManager();
    public GameScreen(Game2D game, String username) {
        this.game = game;

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameCollisionListener());

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenConfig.screenWidth, ScreenConfig.screenHeight);

        Gdx.input.setInputProcessor(inputHandler);
        player = new Player(username);

        Pixmap pm = new Pixmap(Gdx.files.internal("cursor_image.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();

        mapManager.dispatchMap("room", new Vector2(11 * ScreenConfig.originalTileSize, 9 * ScreenConfig.originalTileSize), Direction.DOWN);
//        mapManager.dispatchMap("room");
//        mapManager.dispatchMap("GLE202");
//        mapManager.dispatchMap("GYM");
//        mapManager.dispatchMap("FINAL_NGE_ROOM");
//        mapManager.dispatchMap("LIBRARY");
//        mapManager.dispatchMap("NGE_EXTERIOR");
//        mapManager.dispatchMap("TEST_GLE");
    }

    @Override
    public void show() {

    }

    //ideally update entites then draw entities
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.zoom = 0.5F;
        camera.update();
        world.step(1/60f, 6, 2);

        // Clear screen

        // Render tilemap
        batch.setProjectionMatrix(camera.combined);

        mapManager.update();

        player.update();

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
