package com.mygdx.game.Game2D.Entities.NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.Pattern.Pattern;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Inventory.Inventory;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.ScreenConfig;

import java.util.ArrayList;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Screens.GameScreen.world;

public class NPC extends Entity {
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

    public Pattern movement;

    public NPC(Pattern pattern){
        direction = Direction.DOWN;
        movement = pattern;

        textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/leo.atlas"));

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

//        sprite.setPosition(10 * ScreenConfig.tileSize, 10 * ScreenConfig.tileSize);

        speed = 150;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(8 * ScreenConfig.tileSize, 8 * ScreenConfig.tileSize);
        boxBody = world.createBody(bodyDef);
        boxBody.setLinearDamping(50f);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(sprite.getWidth()  /3, sprite.getHeight() / 8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;

        Fixture fixture = boxBody.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public void move(){
        if(movement.getCurrentDirection() == Direction.UP){
            boxBody.applyLinearImpulse(new Vector2(0, speed), boxBody.getWorldCenter(), true);
        }else if(movement.getCurrentDirection() == Direction.DOWN){
            boxBody.applyLinearImpulse(new Vector2(0, -speed), boxBody.getWorldCenter(), true);
        }else if(movement.getCurrentDirection() == Direction.LEFT){
            boxBody.applyLinearImpulse(new Vector2(-speed, 0), boxBody.getWorldCenter(), true);
        }else if(movement.getCurrentDirection() == Direction.RIGHT){
            boxBody.applyLinearImpulse(new Vector2(speed, 0), boxBody.getWorldCenter(), true);
        }
    }

    public void render(){
//        System.out.println("Current dir: " + movement.getCurrentDirection());
        if((movement.getCurrentDirection() == Direction.LEFT || movement.getCurrentDirection() == Direction.RIGHT)
                && boxBody.getPosition().x % ScreenConfig.tileSize == 0){
//            movement.nextDirection();
        }else if((movement.getCurrentDirection() == Direction.UP || movement.getCurrentDirection() == Direction.DOWN)
            && boxBody.getPosition().y % ScreenConfig.tileSize == 0){
            System.out.println("Nisud");
//            movement.nextDirection();
        }
        move();

        sprite.setPosition(boxBody.getPosition().x - sprite.getWidth() / 2, boxBody.getPosition().y - sprite.getHeight() / 7);
        stateTime += Gdx.graphics.getDeltaTime();

//        if(isMoving){
            switch (movement.getCurrentDirection()) {
                case UP -> frame = upAnimation.getKeyFrame(stateTime, true);
                case DOWN -> frame = downAnimation.getKeyFrame(stateTime, true);
                case LEFT -> frame = leftAnimation.getKeyFrame(stateTime, true);
                case RIGHT -> frame = rightAnimation.getKeyFrame(stateTime, true);
            }
//        }else{
//            switch (movement.getCurrentDirection()) {
//                case UP -> frame = idleUpAnimation.getKeyFrame(stateTime, true);
//                case DOWN -> frame = idleDownAnimation.getKeyFrame(stateTime, true);
//                case LEFT -> frame = idleLeftAnimation.getKeyFrame(stateTime, true);
//                case RIGHT -> frame = idleRightAnimation.getKeyFrame(stateTime, true);
//            }
//        }
        sprite.setRegion(frame);
        sprite.draw(batch);
    }

}
