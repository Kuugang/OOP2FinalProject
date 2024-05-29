package com.mygdx.game.Game2D.World.Maps;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.NPC1;
import com.mygdx.game.Game2D.Manager.TextureAtlasNPC;
import com.mygdx.game.Game2D.World.GameMap;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.Game2D.Screens.GameScreen.*;

public class ROOM extends GameMap {
    public ROOM(String map){
        super(map);
    }

    public void setNPCS(){
        NPC1 gymNPC = new NPC1(100);
        gymNPC.setTextureAtlas(TextureAtlasNPC.NPC1.getPath());
        gymNPC.setDialogues(new ArrayList<>(Arrays.asList(
                "No pain, no gain.",
                "Feel the burn!",
                "Leg day is the best day.",
                "Stay hydrated.",
                "I'm hitting the gym.",
                "Lift heavy, lift smart.",
                "Fitness is a lifestyle.",
                "What’s your PR (personal record)?",
                "Don't skip leg day.",
                "Eat clean, train dirty.",
                "Train insane or remain the same.",
                "The only bad workout is the one that didn’t happen.",
                "Stronger every day."
        )));
        gymNPC.setToStay(Entity.Direction.RIGHT);
        gymNPC.setPosition(new Vector2(29, 9));

        NPC1 anotherNPC = new NPC1(100);
        anotherNPC.setTextureAtlas(TextureAtlasNPC.NPC7.getPath());
        anotherNPC.setPosition(new Vector2(13, 9));
        anotherNPC.setDialogues(new ArrayList<>(Arrays.asList(
                "Sooo Warm...",
                "Hopefully it will be rainy day soon",
                "Zed gamay oten",
                "Goodluck on your studies"
        )));
        anotherNPC.setToStay(Entity.Direction.UP);

        npcManager.addNPC(gymNPC).addNPC(anotherNPC);

        npcManager.getNPCs().forEach(npc -> bodies.add(npc.boxBody));
        player.setDialogue();
    }
}