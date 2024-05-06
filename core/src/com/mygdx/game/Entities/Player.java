package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.BattleRoyaleGame;
import com.mygdx.game.Network.Packets.Packet02Move;
import com.mygdx.game.ScreenConfig;

public class Player {
    public String username;
    public float speed;
    public Sprite spritePlayer;
    public float rotationSpeed;

    public Player(String username, double x, double y) {
        this.username = username;
        speed = 100;
        spritePlayer = new Sprite(new Texture(Gdx.files.internal("Hull_01.png")));
        spritePlayer.setSize(ScreenConfig.tileSize, ScreenConfig.tileSize);
        spritePlayer.setPosition((float) x, (float) y);
        spritePlayer.setOrigin(spritePlayer.getWidth() / 2, spritePlayer.getHeight() / 2);
        rotationSpeed = 10;
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            float dx = (float) (Math.cos(Math.toRadians(spritePlayer.getRotation() + 90)) * speed * Gdx.graphics.getDeltaTime()),
            dy = (float) (Math.sin(Math.toRadians(spritePlayer.getRotation() + 90)) * speed * Gdx.graphics.getDeltaTime());
            spritePlayer.setX(dx + spritePlayer.getX());
            spritePlayer.setY(dy + spritePlayer.getY());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            float dx = (float) (Math.cos(Math.toRadians(spritePlayer.getRotation() + 90)) * speed * Gdx.graphics.getDeltaTime()),
                    dy = (float) (Math.sin(Math.toRadians(spritePlayer.getRotation() + 90)) * speed * Gdx.graphics.getDeltaTime());
            spritePlayer.setX(-dx + spritePlayer.getX());
            spritePlayer.setY(-dy + spritePlayer.getY());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            spritePlayer.rotate(rotationSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            spritePlayer.rotate(-rotationSpeed);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            Packet02Move packet02Move = new Packet02Move(this.username, (int) spritePlayer.getX(), (int) spritePlayer.getX());
            packet02Move.writeData(BattleRoyaleGame.gameClient);
        }
    }

    public String getUsername() {
        return username;
    }

    public double getX() {
        return spritePlayer.getX();
    }

    public double getY() {
        return spritePlayer.getY();
    }

    public void setX(double x) {
        spritePlayer.setX((float) x);
    }

    public void setY(double y) {
        spritePlayer.setY((float) y);
    }

    public float getWidth() {
        return spritePlayer.getWidth();
    }

    public float getHeight() {
        return spritePlayer.getHeight();
    }
}
