package com.mygdx.game.Game2D.Entities.NPC.Pattern;

import com.mygdx.game.Game2D.States.Direction;

import java.util.ArrayList;

public abstract class Pattern {
    int i;
    int length;
    Direction currentDirection;
    ArrayList<Direction> directions;

    public Pattern(int length) {
        directions = new ArrayList<>();
        i = 0;

        this.length = length;

        setDirection();

        setCurrentDirection();

    }

    protected abstract void setDirection();

    public void setCurrentDirection(){
        currentDirection = directions.get(0);
    }

    public void nextDirection() {
        if(atEnd()) i = 0;
        else i++;

        currentDirection = directions.get(i);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean atEnd(){
        return i == length - 1;
    }

    public void nextStateDirection() {
        Direction checkPoint = currentDirection;
        while(checkPoint == getCurrentDirection())
            nextDirection();
    }

}