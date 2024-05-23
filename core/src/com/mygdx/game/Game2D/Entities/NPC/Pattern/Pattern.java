package com.mygdx.game.Game2D.Entities.NPC.Pattern;

import com.mygdx.game.Game2D.Entities.Entity;

import java.util.ArrayList;

public abstract class Pattern {
    int i;
    ArrayList<Entity.Direction> directions;
    Entity.Direction currentDirection;
    int length;

    public Pattern(int length) {
        directions = new ArrayList<>();
        i = 0;

        this.length = length;

        setDirection();

        setCurrentDirection();
    }

    protected abstract void setDirection();

    public void setCurrentDirection(){
        currentDirection = directions.getFirst();
    }

    public void nextDirection() {
        if(atEnd())
            i = 0;

        else i++;

        currentDirection = directions.get(i);
    }

    public Entity.Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean atEnd(){
        return i == length - 1;
    }
}