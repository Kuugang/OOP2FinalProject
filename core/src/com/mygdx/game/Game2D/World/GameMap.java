package com.mygdx.game.Game2D.World;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.util.AbstractMap;
import java.util.ArrayList;

import static com.mygdx.game.Game2D.GameScreen.world;

public abstract class GameMap {
    public TiledMap map;
    MapLayer collisionObjectLayer;
    MapLayer exitCollisionsLayer;
    MapObjects objectsCollisions;
    MapObjects exits = new MapObjects();
    ArrayList <Body> bodies = new ArrayList<>();

    public GameMap setMap(String path){
        this.map = new TmxMapLoader().load(path);
        return this;
    }

    public void setCollisions(){
        System.out.println("WTF");
        collisionObjectLayer = map.getLayers().get("collisions");
        exitCollisionsLayer = map.getLayers().get("exits");

        if(collisionObjectLayer == null && exitCollisionsLayer == null)return;
//        if(collisionObjectLayer == null)return;

        objectsCollisions = collisionObjectLayer.getObjects();
//        exits = exitCollisionsLayer.getObjects();
        System.out.println(objectsCollisions.getCount());


        for (MapObject object : exits) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                BodyDef collisionBodyDef = new BodyDef();
                collisionBodyDef.type = BodyDef.BodyType.StaticBody;

                float centerX = rectangle.x + rectangle.width / 2;
                float centerY = rectangle.y + rectangle.height / 2;
                collisionBodyDef.position.set(centerX, centerY);

                Body collisionBody = world.createBody(collisionBodyDef);

                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(rectangle.width / 2, rectangle.height / 2);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = polygonShape;
                fixtureDef.density = 0f;
                fixtureDef.friction = 1.0f;

                Fixture fixture = collisionBody.createFixture(fixtureDef);
                fixture.setUserData(new AbstractMap.SimpleEntry<>("exit", "GLE202"));
                bodies.add(collisionBody);
            }
        }


        for (MapObject object : objectsCollisions) {
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
        System.out.println(bodies.size());
        for(Body body : bodies){
            world.destroyBody(body);
        }
    }

    public abstract void update();
}
