package com.mygdx.game.Game2D.World;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game2D.States.Direction;

public class MapExit {
    public String nextMap;
    public Vector2 playerPosition;
    public Direction playerDirection;

    public MapExit(String nextMap, Vector2 playerPosition, Direction playerDirection) {
        this.nextMap = nextMap;
        this.playerPosition = playerPosition;
        this.playerDirection = playerDirection;
    }
}
