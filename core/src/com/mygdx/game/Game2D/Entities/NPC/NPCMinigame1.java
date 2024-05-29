package com.mygdx.game.Game2D.Entities.NPC;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class NPCMinigame1 extends NPC {
    public NPCMinigame1(int length) {
        super(length);
        randomizeNPC();
        setDirection(Direction.DOWN);
    }

    public void randomizeNPC(){
        Random random = new Random();
        int randomInt1 = random.nextInt(11 - 7 + 1) + 7;
        int randomInt2 = random.nextInt(17 - 13 + 1) + 13;

        if(random.nextInt(2) == 1){
            setPosition(new Vector2(randomInt1, 20));
        }else{
            setPosition(new Vector2(randomInt2, 20));
        }

        float randomSpeed = random.nextFloat(180 - 140 + 1) + 140;
        setSpeed(randomSpeed);
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
        this.render();
    }
}
