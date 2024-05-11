package com.mygdx.game.Game2D.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.GameScreen;
import com.mygdx.game.Game2D.Handlers.InputHandler;
import com.mygdx.game.Game2D.Inventory.Inventory;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.ScreenConfig;


import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Game2D.shape;
import static com.mygdx.game.Game2D.GameScreen.*;

public class Player extends Entity {
    //Making the fields static because of the assumption that there's no other player beside ourselves
    static public float x, y;
    static public Sprite sprite;
    static public String username;

    public int hp;
    public int charisma;
    public int intelligence;
    public int agility;
    public Inventory inventory;

    TextureAtlas textureAtlas;
    Animation<TextureRegion> upAnimation;
    Animation<TextureRegion> downAnimation;
    Animation<TextureRegion> leftAnimation;
    Animation<TextureRegion> rightAnimation;
    Animation<TextureRegion> idleUpAnimation;
    Animation<TextureRegion> idleDownAnimation;
    Animation<TextureRegion> idleLeftAnimation;
    Animation<TextureRegion> idleRightAnimation;
    public boolean isMoving = false;
    TextureRegion frame;
    float stateTime = 0F;
    Body boxBody;
    public Player(String username){
        textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/player.atlas"));

        upAnimation = new Animation<>(0.10f, textureAtlas.findRegions("move_up"));
        downAnimation = new Animation<>(0.10f, textureAtlas.findRegions("move_down"));
        leftAnimation = new Animation<>(0.10f, textureAtlas.findRegions("move_left"));
        rightAnimation = new Animation<>(0.10f, textureAtlas.findRegions("move_right"));

        idleUpAnimation = new Animation<>(0.10f, textureAtlas.findRegions("idle_up"));
        idleDownAnimation = new Animation<>(0.10f, textureAtlas.findRegions("idle_down"));
        idleLeftAnimation = new Animation<>(0.10f, textureAtlas.findRegions("idle_left"));
        idleRightAnimation = new Animation<>(0.10f, textureAtlas.findRegions("idle_right"));

        upAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        downAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        leftAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        rightAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        idleUpAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        idleDownAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        idleLeftAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        idleRightAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        direction = Direction.DOWN;
        frame = idleDownAnimation.getKeyFrame(0);

        sprite = new Sprite(rightAnimation.getKeyFrame(0));
        x = (float) Gdx.graphics.getWidth() / 2;
        y = (float) Gdx.graphics.getHeight() / 2;

        sprite.setPosition(5 * ScreenConfig.tileSize, 5 * ScreenConfig.tileSize);

        speed = 150;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 100);
        boxBody = world.createBody(bodyDef);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(sprite.getWidth()  /3, sprite.getHeight() / 8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;

        Fixture fixture = boxBody.createFixture(fixtureDef);
        fixture.setUserData("player");
        boxBody.setLinearDamping(10f);
        Player.username = username;
    }


    public void update(){
        isMoving = InputHandler.A || InputHandler.S || InputHandler.W || InputHandler.D;
        sprite.setPosition(boxBody.getPosition().x - sprite.getWidth() / 2, boxBody.getPosition().y - sprite.getHeight() / 7);

        if(InputHandler.A){
            direction = Direction.LEFT;
            boxBody.applyLinearImpulse(new Vector2(-speed, 0), boxBody.getWorldCenter(), true);
        }

        if(InputHandler.D){
            direction = Direction.RIGHT;
            boxBody.applyLinearImpulse(new Vector2(speed, 0), boxBody.getWorldCenter(), true);
        }

        if(InputHandler.W){
            direction = Direction.UP;

            if(InputHandler.D) {
                boxBody.applyLinearImpulse(new Vector2(-speed * 0.3F, speed *  0.7F), boxBody.getWorldCenter(), true);
            }else if(InputHandler.A) {
                boxBody.applyLinearImpulse(new Vector2(speed * 0.3F, speed *  0.7F), boxBody.getWorldCenter(), true);
            }else{
                boxBody.applyLinearImpulse(new Vector2(0, speed), boxBody.getWorldCenter(), true);
            }
        }
        if(InputHandler.S){
            direction = Direction.DOWN;
            if(InputHandler.D) {
                boxBody.applyLinearImpulse(new Vector2(-speed * 0.3F, -speed * 0.7F), boxBody.getWorldCenter(), true);
            }else if(InputHandler.A) {
                boxBody.applyLinearImpulse(new Vector2(speed * 0.3F, -speed * 0.7F), boxBody.getWorldCenter(), true);
            }else{
                boxBody.applyLinearImpulse(new Vector2(0 , -speed), boxBody.getWorldCenter(), true);
            }
        }

    }
    public void render(){
        stateTime += Gdx.graphics.getDeltaTime();

        if(isMoving){
            switch (direction) {
                case UP -> frame = upAnimation.getKeyFrame(stateTime, true);
                case DOWN -> frame = downAnimation.getKeyFrame(stateTime, true);
                case LEFT -> frame = leftAnimation.getKeyFrame(stateTime, true);
                case RIGHT -> frame = rightAnimation.getKeyFrame(stateTime, true);
            }
        }else{
            switch (direction) {
                case UP -> frame = idleUpAnimation.getKeyFrame(stateTime, true);
                case DOWN -> frame = idleDownAnimation.getKeyFrame(stateTime, true);
                case LEFT -> frame = idleLeftAnimation.getKeyFrame(stateTime, true);
                case RIGHT -> frame = idleRightAnimation.getKeyFrame(stateTime, true);
            }
        }
        sprite.setRegion(frame);
        sprite.draw(batch);

        batch.end();
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.end();
        batch.begin();
    }

    public float getX(){
        return sprite.getX() + sprite.getWidth() / 2;
    }

    public float getY(){
        return sprite.getY() + sprite.getHeight() / 2;
    }

    public float getWidth(){
        return sprite.getWidth();
    }

    public float getHeight(){
        return sprite.getHeight();
    }
}