package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Inventory.Inventory;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.ScreenConfig;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Game2D.resourceManager;
import static com.mygdx.game.Game2D.Screens.GameScreen.*;

public class Player extends Entity {
    //Making the fields static because of the assumption that there's no other player beside ourselves
    static public String username;

    public int hp;
    public int charisma;
    public int intelligence;
    public int agility;
    public Inventory inventory;

    public boolean isMoving = false;
    TextureRegion frame;
    float stateTime = 0F;
    Body boxBody;
    public Player(){

        direction = Direction.DOWN;
        frame = resourceManager.idleDownAnimation.getKeyFrame(0);

        sprite = new Sprite(resourceManager.rightAnimation.getKeyFrame(0));
        x = (float) Gdx.graphics.getWidth() / 2;
        y = (float) Gdx.graphics.getHeight() / 2;

        sprite.setPosition(5 * ScreenConfig.tileSize, 5 * ScreenConfig.tileSize);

        speed = 150;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 100);
        boxBody = world.createBody(bodyDef);
        boxBody.setLinearDamping(50f);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(sprite.getWidth()  /3, sprite.getHeight() / 8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;

        Fixture fixture = boxBody.createFixture(fixtureDef);
        fixture.setUserData("player");
        Player.username = username;
    }


    public void update(){
        isMoving = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) ||
                Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.W);
        sprite.setPosition(boxBody.getPosition().x - sprite.getWidth() / 2, boxBody.getPosition().y - sprite.getHeight() / 7);

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            direction = Direction.LEFT;
            boxBody.applyLinearImpulse(new Vector2(-speed, 0), boxBody.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            direction = Direction.RIGHT;
            boxBody.applyLinearImpulse(new Vector2(speed, 0), boxBody.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            direction = Direction.UP;
            boxBody.applyLinearImpulse(new Vector2(0, speed), boxBody.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            direction = Direction.DOWN;
            boxBody.applyLinearImpulse(new Vector2(0 , -speed), boxBody.getWorldCenter(), true);
        }
    }

    public void render(){
        stateTime += Gdx.graphics.getDeltaTime();
        if(isMoving){
            switch (direction) {
                case UP -> frame = resourceManager.upAnimation.getKeyFrame(stateTime, true);
                case DOWN -> frame = resourceManager.downAnimation.getKeyFrame(stateTime, true);
                case LEFT -> frame = resourceManager.leftAnimation.getKeyFrame(stateTime, true);
                case RIGHT -> frame = resourceManager.rightAnimation.getKeyFrame(stateTime, true);
            }
        }else{
            switch (direction) {
                case UP -> frame = resourceManager.idleUpAnimation.getKeyFrame(stateTime, true);
                case DOWN -> frame = resourceManager.idleDownAnimation.getKeyFrame(stateTime, true);
                case LEFT -> frame = resourceManager.idleLeftAnimation.getKeyFrame(stateTime, true);
                case RIGHT -> frame = resourceManager.idleRightAnimation.getKeyFrame(stateTime, true);
            }
        }
        sprite.setRegion(frame);
        sprite.draw(batch);
    }

    public void setPosition(Vector2 position){
        this.boxBody.setTransform(position, 0);
    }

    public void setDirection(Direction direction){
        this.direction = direction;
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