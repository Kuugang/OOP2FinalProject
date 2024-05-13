package com.mygdx.game.Game2D.Entities.NPC.Pattern;

import com.mygdx.game.Game2D.States.Direction;

public class DownPattern extends Pattern {
    public DownPattern(int length) {
        super(length);
    }

    @Override
    protected void setDirection() {
        for(int i = 0; i < length; i++)
            directions.add(Direction.DOWN);
    }
}
