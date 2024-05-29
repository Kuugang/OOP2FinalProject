package com.mygdx.game.Game2D.Entities.NPC;

public class NPC1 extends NPC {

    public NPC1(int length) {
        super(length);
        setTextureAtlas("atlas/leo.atlas");
    }

    @Override
    public void update() {
        move();
    }
}
