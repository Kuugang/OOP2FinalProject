package com.mygdx.game.Game2D.Entities.NPC.Pattern;


import com.mygdx.game.Game2D.Entities.Entity;


public class BoxPattern extends Pattern {

    public BoxPattern() {
        super(3);

        setDirection();

        setCurrentDirection();
    }

    @Override
    protected void setDirection() {
        for(int i = 0; i < 1; i++)
            directions.add(Entity.Direction.LEFT);
        for(int i = 0; i < 1; i++)
            directions.add(Entity.Direction.DOWN);
        for(int i = 0; i < 1; i++)
            directions.add(Entity.Direction.RIGHT);
        for(int i = 0; i < 1; i++)
            directions.add(Entity.Direction.UP);
    }
}