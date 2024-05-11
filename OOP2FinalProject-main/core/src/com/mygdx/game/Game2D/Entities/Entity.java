package com.mygdx.game.Game2D.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.ScreenConfig;

public class Entity {
    public int speed;
    public Sprite up1, up2, upStay, right1, right2, rightStay, down1, down2, downStay, left1, left2, leftStay;
    public Direction direction;
}
