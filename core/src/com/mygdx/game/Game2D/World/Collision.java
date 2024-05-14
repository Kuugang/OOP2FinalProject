package com.mygdx.game.Game2D.World;

public enum Collision {
    PLAYER((short) 0x0001),
    NPC((short) 0x0002),
    WALL((short) 0x0004);
    private final short value;

    Collision(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
