package com.mygdx.game.Game2D.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.States.Direction;

public class Entity {
    public float x, y;
    public Sprite sprite;
    public int speed;
    public Direction direction;
    protected Game2D game;
}
