package com.mygdx.game.Game2D.Entities.NPC;

import java.util.List;

public class HouseNPC extends NPC {

    public HouseNPC(int length) {
        super(length);

        dialogues.addAll(List.of(new String[]{
                "Louise Batig Nawong",
                "Zed Gamay Oten",
                "PE",
                "Special Topics in Mathematics",
                "OOP2",
                "DAA",
                "Beautiful Mistakes"
        }));
    }

    @Override
    public void update() {
        move();
    }
}
