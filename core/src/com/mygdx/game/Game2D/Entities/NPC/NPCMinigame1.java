package com.mygdx.game.Game2D.Entities.NPC;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game2D.World.CollisionType;
import com.mygdx.game.Game2D.World.Maps.Minigames.MINIGAME1;

import java.util.Random;

public class NPCMinigame1 extends NPC {
    Random random = new Random();
    int level;
    public NPCMinigame1(int length, int level) {
        super(length);
        this.level = level;
        randomizeNPC();
        setDirection(Direction.DOWN);
        fixtureDef.filter.maskBits = (short) (CollisionType.WALL.getValue() | CollisionType.PLAYER.getValue() | CollisionType.NPC.getValue());
    }

    public void randomizeNPC(){
        int randomInt1 = random.nextInt(11 - 7 + 1) + 7;
        int randomInt2 = random.nextInt(17 - 13 + 1) + 13;

        if(random.nextInt(2) == 1){
            setPosition(new Vector2(randomInt1, 29));
        }else{
            setPosition(new Vector2(randomInt2, 29));
        }

        float speedLowerBound = 0;
        float speedUpperBound = 0;

        if(level == 1){
            speedLowerBound = 140;
            speedUpperBound = 180;
        }
        if(level == 2){
            speedLowerBound = 180;
            speedUpperBound = 220;
        }

        if(level == 3){
            speedLowerBound = 120;
            speedUpperBound = 260;
        }

        setSpeed(random.nextFloat(speedUpperBound - speedLowerBound + 1) + speedLowerBound);
    }

    public void move(){
        boxBody.applyLinearImpulse(new Vector2(0, -speed / 2), boxBody.getWorldCenter(), true);

        if(getPosition().y <= 0.21515606){
            randomizeNPC();
        }
    }

    @Override
    public void update() {
        move();
        float speedLowerBound = 0;
        float speedUpperBound = 0;

        if(level == 1){
            speedLowerBound = 140;
            speedUpperBound = 180;
        }

        if(level == 2){
            speedLowerBound = 180;
            speedUpperBound = 220;
        }

        if(level == 3){
            speedLowerBound = 120;
            speedUpperBound = 260;
        }

        setSpeed(random.nextFloat(speedUpperBound - speedLowerBound + 1) + speedLowerBound);
    }
}
