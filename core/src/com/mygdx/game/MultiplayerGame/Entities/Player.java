package com.mygdx.game.MultiplayerGame.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MultiplayerGame.BattleRoyaleGame;
import com.mygdx.game.MultiplayerGame.Network.Packets.Packet02Move;
import com.mygdx.game.ScreenConfig;

import java.util.ArrayList;

import static com.mygdx.game.MultiplayerGame.BattleRoyaleGame.*;
import static com.mygdx.game.MultiplayerGame.GameScreen.getMouse;

public class Player {
    public String username;
    public float x, y; //Location
    private Vector2 gunTip;
    public float speed;
    public Rectangle playerBounds; // Parameter
    public Texture gun;
    public static ArrayList<Bullet> playersBullets = new ArrayList<>();
    private int reloadTime = 0;
    private int cooldown = 100;
    public Sprite spritePlayer;
    public Sprite spriteGun;
    public float rotationSpeed;
    private float gunRotationSpeed = 100;

    public Player(String username, float x, float y) {
        this.username = username;
        speed = 100;
        playerBounds = new Rectangle(x, y, ScreenConfig.tileSize, ScreenConfig.tileSize);
        gun = new Texture(Gdx.files.internal("Multiplayer Game/gun-test.png"));
        this.x = x + (float) this.gun.getWidth() / 2;
        this.y = y + (float) this.gun.getHeight() / 2;
        this.gunTip = new Vector2(this.x, this.y + 100);

        spritePlayer = new Sprite(new Texture(Gdx.files.internal("Multiplayer Game/Hull_01.png")));
        spritePlayer.setSize(ScreenConfig.tileSize, ScreenConfig.tileSize);
        spritePlayer.setPosition(x, y);
        spritePlayer.setOrigin(spritePlayer.getWidth() / 2, spritePlayer.getHeight() / 2);
        rotationSpeed = 10;

        spriteGun = new Sprite(new Texture(Gdx.files.internal("Multiplayer Game/gun-test.png")));
        spriteGun.setSize(ScreenConfig.tileSize, ScreenConfig.tileSize);
        spriteGun.setPosition(x, y);
        spriteGun.setOrigin(spriteGun.getWidth() / 2,spriteGun.getHeight() / 2);
    }

    public void shoot(){
        float directionX = getMouse().x - this.x;
        float directionY = getMouse().y - this.y;
        float angleDegrees = (float) Math.toDegrees(Math.atan2(directionY, directionX));
        playersBullets.add(new Bullet(this.x , this.y, angleDegrees));
        reloadTime = cooldown;
    }

    float currentAngleDegrees = 0;
    float targetAngleDegrees = 0;
    public void draw(){
        float dx = getMouse().x - this.x;
        float dy = getMouse().y - this.y;
        targetAngleDegrees = (float) Math.toDegrees(Math.atan2(dy, dx));

//        this.gunTip.y = y + 100;
//        this.gunTip.x = x;

        if (currentAngleDegrees != targetAngleDegrees) {
            float angleDiff = targetAngleDegrees - currentAngleDegrees;
            if (angleDiff > 180) {
                angleDiff -= 360;
            } else if (angleDiff < -180) {
                angleDiff += 360;
            }
            float maxRotation = gunRotationSpeed * Gdx.graphics.getDeltaTime();
            float rotation = Math.signum(angleDiff) * Math.min(Math.abs(angleDiff), maxRotation);
            currentAngleDegrees += rotation;
        }

        font.draw(batch, this.getUsername(), this.spritePlayer.getX() + 50, this.spritePlayer.getY() + 50);

        this.spritePlayer.draw(batch);

        batch.draw(gun,
                this.x - (float) this.gun.getWidth() / 2,
                this.y - (float) this.gun.getHeight() / 2,
                (float) this.gun.getWidth() / 2,
                (float) this.gun.getHeight() / 2,
                this.gun.getWidth(),
                this.gun.getHeight(),
                1,
                1,
                currentAngleDegrees - 90,
                0, 0, this.gun.getWidth(), this.gun.getHeight(), false, false);



        batch.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.circle(this.gunTip.x, this.gunTip.y, 5);
        shape.end();
        batch.begin();
    }


    public void update(){
        reloadTime--;
        if(Gdx.input.isButtonPressed(0) && reloadTime < 0)shoot();

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
            spritePlayer.rotate(-rotationSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            spritePlayer.rotate(rotationSpeed);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            Packet02Move packet02Move = new Packet02Move(this.username, (int) spritePlayer.getX(), (int) spritePlayer.getX());
            packet02Move.writeData(BattleRoyaleGame.gameClient);
        }
    }

    public String getUsername() {
        return username;
    }

    public float getX() {
        return spritePlayer.getX();
    }

    public float getY() {
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