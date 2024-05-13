package com.mygdx.game.Game2D.Entities.NPC.Pattern;

import com.mygdx.game.Game2D.States.Direction;

import java.util.ArrayList;

public abstract class Pattern {
    int i;
    ArrayList<Direction> directions;
    Direction currentDirection;

    public Pattern() {
        directions = new ArrayList<>();
        i = 0;
    }

    protected abstract void setDirection();

    public void setCurrentDirection(){
        currentDirection = directions.get(0);
    }

    public void nextDirection() {
        if(i == directions.size() - 1)
            i = 0;
        else
            i++;
        System.out.println(i);

        currentDirection = directions.get(i);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}