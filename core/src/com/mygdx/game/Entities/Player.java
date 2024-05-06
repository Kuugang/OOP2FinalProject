package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.BattleRoyaleGame;
import com.mygdx.game.Network.Packets.Packet02Move;
import com.mygdx.game.ScreenConfig;

public class Player {
    public String username;
    public double x, y; //Location
    public float speed;
    public Rectangle playerBounds; // Parameter

    public Player(String username, double x, double y) {
        this.username = username;
        this.x = x;
        this.y = y;
        speed = 100;
        playerBounds = new Rectangle((float) x, (float) y, ScreenConfig.tileSize, ScreenConfig.tileSize);
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.y += this.speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            this.y -= this.speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.x += this.speed * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.x -= this.speed * Gdx.graphics.getDeltaTime();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            Packet02Move packet02Move = new Packet02Move(this.username, (int) this.x, (int)this.y);
            packet02Move.writeData(BattleRoyaleGame.gameClient);
        }
    }

    public String getUsername() {
        return username;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
