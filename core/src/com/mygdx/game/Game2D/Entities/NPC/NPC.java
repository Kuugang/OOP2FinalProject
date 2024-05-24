package com.mygdx.game.Game2D.Entities.NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.Pattern.Pattern;
import com.mygdx.game.Game2D.Entities.NPC.Pattern.*;
import com.mygdx.game.Game2D.Screens.GameScreen;
import com.mygdx.game.Game2D.World.CollisionType;
import com.mygdx.game.ScreenConfig;

import java.util.ArrayList;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Screens.GameScreen.gameState;
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
    public boolean isMoving;
    TextureRegion frame;
    float stateTime = 0F;
    int length; //Length in seconds in which the entity do a movement
    int movementCounter; //A counter for fixing NPC movement when encountering a collision
    public boolean setStay;

    public NPC(int length){
        direction = Direction.DOWN;
        setStay = isMoving = false;

        movementCounter = (int) (length * speed);
        this.length = length;
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

        speed = 20;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(8 * ScreenConfig.tileSize, 8 * ScreenConfig.tileSize);
        boxBody = world.createBody(bodyDef);
        boxBody.setLinearDamping(50f);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(sprite.getWidth() / 3, sprite.getHeight() / 8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.filter.categoryBits = CollisionType.NPC.getValue();
        fixtureDef.filter.maskBits = CollisionType.WALL.getValue();  // Collides with walls only

        Fixture fixture = boxBody.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public void move(){
        if(setStay)
            return;

        if(movementCounter == length * speed)
            newMovement();

        if(direction == Direction.UP){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(0, speed), boxBody.getWorldCenter(), true);
            direction = Direction.UP;
        }else if(direction == Direction.DOWN){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(0, -speed), boxBody.getWorldCenter(), true);
            direction = Direction.DOWN;
        }else if(direction == Direction.LEFT){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(-speed, 0), boxBody.getWorldCenter(), true);
            direction = Direction.LEFT;
        }else if(direction == Direction.RIGHT){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(speed, 0), boxBody.getWorldCenter(), true);
            direction = Direction.RIGHT;
        }else {
            isMoving = false;
        }

        if((((
                direction == Direction.LEFT || direction == Direction.RIGHT)
                && (int)boxBody.getPosition().x % ScreenConfig.tileSize == 0) || ((direction ==
                Direction.UP || direction == Direction.DOWN)
                && (int)boxBody.getPosition().y % ScreenConfig.tileSize == 0)) && movementCounter / length >= length)
            newMovement();
        else
            movementCounter++;

        if(movementCounter >= length)
            newMovement();

    }

    public void newMovement(){
        setStay = false;
        Direction currentMovement = direction;
        while(currentMovement == direction){
            RandomXS128 randomXS128 = new RandomXS128();
            int randomNumber = randomXS128.nextInt() % 5;
            switch (randomNumber) {
                case 0 -> direction = Direction.DOWN;
                case 1 -> direction = Direction.LEFT;
                case 2 -> direction = Direction.RIGHT;
                case 3 -> direction = Direction.UP;
                case 4 -> direction = Direction.STAY;
            }
        }
        if(direction == Direction.STAY)
            length *= 10;

        movementCounter = 0;
    }

    public void setToStay(int length){
        direction = Direction.STAY;
        this.length = length * 10000;
        movementCounter = 0;
        setStay = true;
    }

    public NPC render(){
        if(gameState == GameScreen.GameState.PAUSED)
            return this;

        float currentX = sprite.getX(), currentY = sprite.getY();
        sprite.setPosition(boxBody.getPosition().x - sprite.getWidth() / 2, boxBody.getPosition().y -
                sprite.getHeight() / 7);

        if(currentX == sprite.getX() && currentY == sprite.getY() && isMoving)
            newMovement();

        stateTime += Gdx.graphics.getDeltaTime();

        if(isMoving && !setStay)
            animation(upAnimation, downAnimation, leftAnimation, rightAnimation);
        else
            animation(idleUpAnimation, idleDownAnimation, idleLeftAnimation, idleRightAnimation);

        sprite.setRegion(frame);

        batch.begin();
        sprite.draw(batch);
        batch.end();
        return this;
    }

    private NPC animation(Animation<TextureRegion> upAnimation, Animation<TextureRegion> downAnimation,
                               Animation<TextureRegion> leftAnimation, Animation<TextureRegion> rightAnimation) {
        switch (direction) {
            case UP -> frame = upAnimation.getKeyFrame(stateTime, true);
            case DOWN -> frame = downAnimation.getKeyFrame(stateTime, true);
            case LEFT -> frame = leftAnimation.getKeyFrame(stateTime, true);
            case RIGHT -> frame = rightAnimation.getKeyFrame(stateTime, true);
        }
        return this;
    }
}
