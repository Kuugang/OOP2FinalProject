package com.mygdx.game.MultiplayerGame.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.MultiplayerGame.BattleRoyaleGame.batch;

public class Bullet {
    public float x, y, dx, dy, angle, speed = 4;
    public Texture bullet;
    public Bullet(float x, float y, float angle){
        this.dx = 0;
        this.dy = 0;
        this.angle = angle;
        bullet = new Texture(Gdx.files.internal("Multiplayer Game/bullet-test.png"));
        this.x = x - (float) this.bullet.getWidth() / 2;
        this.y = y - (float) this.bullet.getHeight() / 2;
    }

    public void draw(){
        batch.draw(bullet,
            this.x,
            this.y,
            (float) this.bullet.getWidth() / 2,
            (float) this.bullet.getHeight() / 2,
            this.bullet.getWidth(),
            this.bullet.getHeight(),
            1,
            1,
            this.angle - 90,
            0, 0, this.bullet.getWidth(), this.bullet.getHeight(), false, false);
    }

    public void update(){
        this.dx = (float) (Math.cos(Math.toRadians(this.angle)) * this.speed);
        this.dy = (float) (Math.sin(Math.toRadians(this.angle)) * this.speed);

        this.x += this.dx;
        this.y += this.dy;
    }
}
