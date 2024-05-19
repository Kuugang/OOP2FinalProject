package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Inventory.Inventory;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Network.Packets.Packet00Login;
import com.mygdx.game.Game2D.Network.Packets.Packet02Move;
import com.mygdx.game.Game2D.Screens.GameScreen;
import com.mygdx.game.Game2D.World.CollisionType;

import static com.mygdx.game.Game2D.Game2D.*;
import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.World.isMultiplayer;

public class Player extends Entity {
    public String username;
    public boolean isCollisionSet;
    public int hp;
    public int charisma;
    public int intelligence;
    public int agility;
    public Inventory inventory;
    public boolean isMoving = false;
    TextureRegion frame;
    float stateTime = 0F;
    public float interactionDistance = 40; //Maximum distance when interacting with other entities

    public Player(String username, Vector2 position, Entity.Direction direction){
        this.x = position.x;
        this.y = position.y;
        this.username = username;
        this.direction = direction;
        speed = 150F;
    }

    public void login(){
        Packet00Login packet = new Packet00Login(username, boxBody.getPosition().x,
                boxBody.getPosition().y, direction, this.map);
        packet.writeData(GameScreen.game.getGameClient());
    }

    public void setCollision() {
        ResourceManager resourceManager = ResourceManager.getInstance();

        frame = resourceManager.idleDownAnimation.getKeyFrame(0);
        sprite = new Sprite(resourceManager.rightAnimation.getKeyFrame(0));
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        boxBody = world.createBody(bodyDef);
        boxBody.setLinearDamping(50f);
        boxBody.setTransform(0, 0, 0);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(sprite.getWidth() / 3, sprite.getHeight() / 8);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;

        fixtureDef.filter.categoryBits = CollisionType.PLAYER.getValue();
        fixtureDef.filter.maskBits = (short) (CollisionType.WALL.getValue() | CollisionType.EXIT.getValue());

        Fixture fixture = boxBody.createFixture(fixtureDef);
        fixture.setUserData(this);
        isCollisionSet = true;
    }

    public void setPosition(float x, float y){
        if(!isCollisionSet)
            setCollision();
        boxBody.setTransform(x, y, 0);
    }

    public void update(){
        isMoving = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) ||
            Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.W);
        //TODO FIX SENDING PACKETS
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            direction = Direction.LEFT;
            boxBody.applyLinearImpulse(new Vector2(-speed, 0), boxBody.getWorldCenter(), true);
            if(isMultiplayer){
                Packet02Move packet = new Packet02Move(username, getX(), getY(), direction, map);
                packet.writeData(GameScreen.game.getGameClient());
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            direction = Direction.RIGHT;
            boxBody.applyLinearImpulse(new Vector2(speed, 0), boxBody.getWorldCenter(), true);

            if(isMultiplayer){
                Packet02Move packet = new Packet02Move(username, getX(), getY(), direction, map);
                packet.writeData(GameScreen.game.getGameClient());
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            direction = Direction.UP;
            boxBody.applyLinearImpulse(new Vector2(0, speed), boxBody.getWorldCenter(), true);

            if(isMultiplayer){
                Packet02Move packet = new Packet02Move(username, getX(), getY(), direction, map);
                packet.writeData(GameScreen.game.getGameClient());
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            direction = Direction.DOWN;
            boxBody.applyLinearImpulse(new Vector2(0 , -speed), boxBody.getWorldCenter(), true);

            if(isMultiplayer){
                Packet02Move packet = new Packet02Move(username, getX(), getY(), direction, map);
                packet.writeData(GameScreen.game.getGameClient());
            }
        }
    }



    private boolean withinInteractionRange(Sprite player, Sprite entity){
        float entitySpriteX = entity.getX(), entitySpriteY = entity.getY();
        float playerSpriteX = player.getX(), playerSpriteY = player.getY();

        Rectangle playerRectangle;
        Rectangle entityRectangle = new Rectangle(entitySpriteX, entitySpriteY - entity.getHeight(),
                entity.getWidth(), entity.getHeight());

        if(direction == Direction.LEFT)
             playerRectangle = new Rectangle(playerSpriteX - interactionDistance,
                     playerSpriteY - player.getHeight(), player.getWidth(), player.getHeight());
        else if(direction == Direction.RIGHT)
            playerRectangle = new Rectangle(playerSpriteX, playerSpriteY - player.getHeight()
                    , player.getWidth() + interactionDistance, player.getHeight());
        else if(direction == Direction.UP)
            playerRectangle = new Rectangle(playerSpriteX, playerSpriteY - player.getHeight(), player.getWidth()
                    , player.getHeight() + interactionDistance);
        else
            playerRectangle = new Rectangle(playerSpriteX, playerSpriteY - player.getHeight() - interactionDistance
                    , player.getWidth(), player.getHeight());

        return playerRectangle.overlaps(entityRectangle);
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
        sprite.setPosition(boxBody.getPosition().x - sprite.getWidth() / 2,
                boxBody.getPosition().y - sprite.getHeight() / 7);
        sprite.setRegion(frame);
        sprite.draw(batch);
        isMoving = false;

        if(Gdx.input.isKeyPressed(Input.Keys.F))
            for(Entity e: mapManager.currentMap.npcs)
                if(withinInteractionRange(sprite, e.sprite))
                    e.setDialogue("Louise Batig Nawong");

        for(Entity e: mapManager.currentMap.npcs)
            if(!e.finishedDialogue)
                e.doDialogue();

        doDialogue();
    }

    public void setPosition(Vector2 position){
        this.boxBody.setTransform(position, 0);
    }

    public Player setDirection(Direction direction){
        this.direction = direction;
        return this;
    }

    public Player setUsername(String username) {
        this.username = username;
        return this;
    }

    public Player setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
        return this;
    }

    public float getWidth(){
        return sprite.getWidth();
    }

    public float getHeight(){
        return sprite.getHeight();
    }
    public String getUsername(){
        return username;
    }
}