package com.mygdx.game.Game2D.Entities.NPC.Pattern;

import com.mygdx.game.Game2D.States.Direction;

import java.util.Arrays;
import java.util.Collection;

public class BoxPattern extends Pattern {

    public BoxPattern() {
        super();

        setDirection();

        setCurrentDirection();
    }

    @Override
    protected void setDirection() {
        for(int i = 0; i < 1; i++)
            directions.add(Direction.LEFT);
        for(int i = 0; i < 1; i++)
            directions.add(Direction.DOWN);
        for(int i = 0; i < 1; i++)
            directions.add(Direction.RIGHT);
        for(int i = 0; i < 1; i++)
            directions.add(Direction.UP);
    }
}