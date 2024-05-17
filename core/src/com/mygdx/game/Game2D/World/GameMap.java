package com.mygdx.game.Game2D.World;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Entities.PlayerMP;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.mygdx.game.Game2D.Screens.GameScreen.world;
import static com.mygdx.game.Game2D.World.MapManager.otherPlayers;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public abstract class GameMap {
    public TiledMap tiledMap;
    public String mapName;
    MapLayer collisionLayer;
    MapObjects collisionMapObjects;
    public MapLayer exitLayer;
    public MapObjects exitMapObjects = new MapObjects();
    public ArrayList <Body> bodies = new ArrayList<>();
    public ArrayList<NPC> npc = new ArrayList<>();
    public int worldWidth, worldHeight;

    public GameMap setMap(String path) {
        this.tiledMap = new TmxMapLoader().load(path);
        return this;
    }

    public GameMap setMapName(String mapName){
        this.mapName = mapName;
        return this;
    }

    public GameMap setWorldWidth(int worldWidth){
        this.worldWidth = worldWidth;
        return this;
    }

    public GameMap setWorldHeight(int worldHeight){
        this.worldHeight = worldHeight;
        return this;
    }

    public abstract void setExits();

    public void setCollisions(){
        collisionLayer = this.tiledMap.getLayers().get("COLLISION_LAYER");

        if(collisionLayer == null)return;

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

    public TiledMap getTiledMap(){
        return tiledMap;
    }

    public void dispose(){
        for(Body body : bodies){
            world.destroyBody(body);
        }
    }


    public void update() {
        tiledMapRenderer.render();

        //TODO FOR MULTIPLAYER
//        for (Map.Entry<String, PlayerMP> entry : otherPlayers.entrySet()) {
//            PlayerMP playerMP = entry.getValue();
//
//            System.out.println(playerMP.map);
//            System.out.println(this.mapName);
//
//            if(Objects.equals(playerMP.map, this.mapName)){
//                System.out.println("PLAYER WHAT NOT ");
//                if(!playerMP.isCollisionSet){
//                    playerMP.setCollision();
//                }
//                entry.getValue().render();
//            }
//        }
    }
}
