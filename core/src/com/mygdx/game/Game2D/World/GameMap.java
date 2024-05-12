package com.mygdx.game.Game2D.World;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public abstract class GameMap {
    public TiledMap map;
    MapLayer collisionLayer;
    MapObjects collisionMapObjects;
    public MapLayer exitLayer;
    public MapObjects exitMapObjects = new MapObjects();
    public ArrayList <Body> bodies = new ArrayList<>();

    public GameMap setMap(String path) {
        this.map = new TmxMapLoader().load(path);
        return this;
    }

    public abstract void setExits();

    public void setCollisions(){
        collisionLayer = map.getLayers().get("Collision");

        if(collisionLayer == null)return;
//        if(collisionObjectLayer == null)return;

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

                collisionBody.createFixture(collisionFixtureDef);
                bodies.add(collisionBody);
            }
        }
    }

    public TiledMap getTiledMap(){
        return map;
    }

    public void dispose(){
        for(Body body : bodies){
            world.destroyBody(body);
        }
    }

    public void update() {
        tiledMapRenderer.render();
        player.render();
    }
}
