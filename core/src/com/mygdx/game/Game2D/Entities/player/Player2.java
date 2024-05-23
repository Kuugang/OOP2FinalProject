package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Inventory.Inventory;
import com.mygdx.game.Game2D.Manager.ResourceManager;

import com.badlogic.gdx.utils.Json.Serializable;

import static com.mygdx.game.Game2D.Game2D.batch;

public class Player2 implements Serializable {
    transient public Sprite sprite;
    public String username;
    public boolean isCollisionSet;
    public int hp;
    public int charisma;
    public int intelligence;
    public int agility;
    public Inventory inventory;
    public boolean isMoving = false;
    private Vector2 lastMapPosition;
    TextureRegion frame;
    Entity.Direction direction;

    public Player2(){

    }
    public Player2(String username, Vector2 position, Entity.Direction direction){
        this.username = username;
        this.direction = direction;

        ResourceManager resourceManager = ResourceManager.getInstance();

        frame = resourceManager.idleDownAnimation.getKeyFrame(0);
        sprite = new Sprite(resourceManager.rightAnimation.getKeyFrame(0));
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public void render(){
        sprite.setPosition(0, 0);
        sprite.setRegion(frame);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void write(Json json) {
        json.writeValue("username", username);
        // Write other fields as needed...
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        username = json.readValue("username", String.class, jsonData);
        // Read other fields as needed...
    }
}
