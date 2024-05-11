package com.mygdx.game.Game2D.World.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.Game2D.World.ExitMap;
import com.mygdx.game.Game2D.World.GameMap;
import com.mygdx.game.ScreenConfig;

import java.util.AbstractMap;

import static com.mygdx.game.Game2D.GameScreen.world;

public class Room extends GameMap {
    @Override
    public void setExits() {
        exitLayer = map.getLayers().get("Exit");
        if(exitLayer == null) return;
        exitMapObjects = exitLayer.getObjects();

        for (MapObject object : exitMapObjects) {
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
                fixture.setUserData(new ExitMap("GLE202", new Vector2(3 * ScreenConfig.originalTileSize, ScreenConfig.originalTileSize), Direction.UP));
                bodies.add(collisionBody);
            }
        }
    }
}