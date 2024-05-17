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
import com.mygdx.game.Game2D.World.CollisionType;
import com.mygdx.game.ScreenConfig;

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
    public boolean isMoving;
    TextureRegion frame;
    float stateTime = 0F;
    Body boxBody;
    int length;
    int movementCounter; //A counter for fixing NPC movement when encountering a collision

    public Pattern movement;

    public NPC(int length){
        direction = Direction.DOWN;
        isMoving = false;
        movementCounter = length;
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

        speed = 50;

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

        movement = new UpPattern(length);
    }

    public void move(){
        if(movementCounter == length * speed)
            newMovement();
        if(movement.getCurrentDirection() == Direction.UP){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(0, speed), boxBody.getWorldCenter(), true);
            direction = Direction.UP;
            movementCounter++;
        }else if(movement.getCurrentDirection() == Direction.DOWN){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(0, -speed), boxBody.getWorldCenter(), true);
            direction = Direction.DOWN;
            movementCounter++;
        }else if(movement.getCurrentDirection() == Direction.LEFT){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(-speed, 0), boxBody.getWorldCenter(), true);
            direction = Direction.LEFT;
            movementCounter++;
        }else if(movement.getCurrentDirection() == Direction.RIGHT){
            isMoving = true;
            boxBody.applyLinearImpulse(new Vector2(speed, 0), boxBody.getWorldCenter(), true);
            direction = Direction.RIGHT;
            movementCounter++;
        }else {
            isMoving = false;
            movementCounter++;
        }
    }

    public void newMovement(){
        Direction currentMovement = movement.getCurrentDirection();
        while(currentMovement == movement.getCurrentDirection()){
            RandomXS128 randomXS128 = new RandomXS128();
            int randomNumber = randomXS128.nextInt() % 5;
            switch (randomNumber) {
                case 0 -> {
                    movement = new DownPattern(length);
                    movementCounter = 0;
                }
                case 1 -> {
                    movement = new LeftPattern(length);
                    movementCounter = 0;
                }
                case 2 -> {
                    movement = new RightPattern(length);
                    movementCounter = 0;
                }
                case 3 -> {
                    movement = new UpPattern(length);
                    movementCounter = 0;
                }
                case 4 -> {
                    movement = new StayPattern((int) (length * speed));
                    movementCounter = 0;
                }
            }
        }

    }

    public NPC render(){
        move();
        if(movement.getCurrentDirection() == Direction.STAY)
            movement.nextDirection();
        else if((movement.getCurrentDirection() == Direction.LEFT || movement.getCurrentDirection() == Direction.RIGHT)
                && (int)boxBody.getPosition().x % ScreenConfig.tileSize == 0){
            movement.nextDirection();
        }else if((movement.getCurrentDirection() == Direction.UP || movement.getCurrentDirection() == Direction.DOWN)
            && (int)boxBody.getPosition().y % ScreenConfig.tileSize == 0){
            movement.nextDirection();
        }

        if(movement.atEnd())
           newMovement();

        sprite.setPosition(boxBody.getPosition().x - sprite.getWidth() / 2, boxBody.getPosition().y -
                sprite.getHeight() / 7);
        stateTime += Gdx.graphics.getDeltaTime();

        if(isMoving)
            animation(upAnimation, downAnimation, leftAnimation, rightAnimation);
        else
            animation(idleUpAnimation, idleDownAnimation, idleLeftAnimation, idleRightAnimation);

        sprite.setRegion(frame);
        sprite.draw(batch);

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
