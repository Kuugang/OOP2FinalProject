package com.mygdx.game.Game2D.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Game2D.Game2D;

public class Entity {
    public float x, y;
    public Sprite sprite;
    public String map;
    public float speed;
    public Direction direction;
    protected Game2D game;

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT, STAY;

        static public Direction getRandomNext() {
            return Direction.values()[MathUtils.random(Direction.values().length - 1)];
        }

        public Direction getOpposite() {
            if (this == LEFT) {
                return RIGHT;
            } else if (this == RIGHT) {
                return LEFT;
            } else if (this == UP) {
                return DOWN;
            } else {
                return UP;
            }
        }
    }

    public enum State {
        IDLE,
        WALKING;
    }
    public void setMap(String map){
        this.map = map;
    }
}