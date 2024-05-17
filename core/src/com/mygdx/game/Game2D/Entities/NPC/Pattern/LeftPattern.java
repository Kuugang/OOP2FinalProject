package com.mygdx.game.Game2D.Entities.NPC.Pattern;
import com.mygdx.game.Game2D.Entities.Entity;

public class LeftPattern extends Pattern{

    public LeftPattern(int length) {
        super(length);
    }

    @Override
    protected void setDirection() {
        for(int i = 0; i < length; i++)
            directions.add(Entity.Direction.LEFT);
    }
}
