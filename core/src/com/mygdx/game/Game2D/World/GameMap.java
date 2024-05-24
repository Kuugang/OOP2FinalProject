package com.mygdx.game.Game2D.World;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Manager.NPCManager;
import com.mygdx.game.ScreenConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public abstract class GameMap {
    public TiledMap tiledMap;
    public String mapName;
    MapLayer collisionLayer;
    MapObjects collisionMapObjects;
    MapObjects mapExitObjects;
    public MapLayer exitLayer;
    public MapObjects exitMapObjects = new MapObjects();
    public ArrayList <Body> bodies = new ArrayList<>();
    protected int layers;
    TiledMapTileLayer FOREGROUND_LAYER, FOREGROUND_LAYER1;
    public NPCManager npcManager = new NPCManager();

    public GameMap setMap(String path) {
        this.tiledMap = new TmxMapLoader().load(path);
        layers = tiledMap.getLayers().getCount();
        FOREGROUND_LAYER = (TiledMapTileLayer) tiledMap.getLayers().get("FOREGROUND_LAYER");
        FOREGROUND_LAYER1 = (TiledMapTileLayer) tiledMap.getLayers().get("FOREGROUND_LAYER1");
        return this;
    }

    public GameMap setMapName(String mapName){
        this.mapName = mapName;
        return this;
    }

    public void setExits(){
        exitLayer = this.tiledMap.getLayers().get("EXIT_LAYER");

        if(exitLayer != null){
            mapExitObjects = exitLayer.getObjects();
            for (MapObject object : mapExitObjects) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                    BodyDef collisionBodyDef = new BodyDef();
                    collisionBodyDef.type = BodyDef.BodyType.StaticBody;

                    collisionBodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2));

                    Body collisionBody = world.createBody(collisionBodyDef);

                    FixtureDef exitFixtureDef = new FixtureDef();
                    PolygonShape shape = new PolygonShape();

                    shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
                    exitFixtureDef.shape = shape;
                    exitFixtureDef.filter.categoryBits = CollisionType.EXIT.getValue();
                    exitFixtureDef.filter.maskBits = CollisionType.PLAYER.getValue();
                    MapProperties properties = object.getProperties();
                    String nextMap = (String) properties.get("nextMap");
                    Entity.Direction direction = Entity.Direction.fromString((String)properties.get("playerDirection"));
                    float playerX;
                    float playerY;
                    if(properties.containsKey("setLastPosition")){
                        playerX = player.getLastMapPosition().x;
                        playerY = player.getLastMapPosition().y;
                    }else{
                        playerX = (float) properties.get("playerX");
                        playerY = (float) properties.get("playerY");
                    }

                    MapExit exit = new MapExit(nextMap, new Vector2(playerX, playerY), direction);

                    collisionBody.createFixture(exitFixtureDef).setUserData(exit);
                    bodies.add(collisionBody);
                    shape.dispose();
                }
            }
        }
    }

    public void setCollisions(){
        collisionLayer = this.tiledMap.getLayers().get("COLLISION_LAYER");

        if(collisionLayer != null){
            collisionMapObjects = collisionLayer.getObjects();
            for (MapObject object : collisionMapObjects) {
                if (object instanceof PolygonMapObject) {
                    Polygon polygon = ((PolygonMapObject) object).getPolygon();

                    BodyDef collisionBodyDef = new BodyDef();
                    collisionBodyDef.type = BodyDef.BodyType.StaticBody;

                    collisionBodyDef.position.set(polygon.getX(), polygon.getY());

                    Body collisionBody = world.createBody(collisionBodyDef);

                    FixtureDef collisionFixtureDef = new FixtureDef();
                    PolygonShape shape = new PolygonShape();
                    shape.set(polygon.getVertices());
                    collisionFixtureDef.shape = shape;
                    collisionFixtureDef.filter.categoryBits = CollisionType.WALL.getValue();

                    collisionBody.createFixture(collisionFixtureDef).setUserData("COLLISION");
                    bodies.add(collisionBody);
                }
            }
        }

    }

    public TiledMap getTiledMap(){
        return tiledMap;
    }

    public void disposeBodies(){
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        this.bodies.clear();
    }

    public void update() {
        ScreenUtils.clear(0, 0, 0, 1);

        tiledMapRenderer.setView(camera);
        for (int i = 0; i < layers; i++) {
            if (tiledMap.getLayers().get(i) == FOREGROUND_LAYER || tiledMap.getLayers().get(i) == FOREGROUND_LAYER1) {
                break;
            }
            tiledMapRenderer.render(new int[]{i});
        }

        player.render();

        for (int i = 0; i < layers; i++) {
            if (tiledMap.getLayers().get(i) == FOREGROUND_LAYER || tiledMap.getLayers().get(i) == FOREGROUND_LAYER1) {
                tiledMapRenderer.render(new int[]{i});
            }
        }
    }
}
