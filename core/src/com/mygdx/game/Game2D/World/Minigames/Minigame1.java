package com.mygdx.game.Game2D.World.Minigames;

import com.mygdx.game.Game2D.Entities.NPC.HouseNPC;
import com.mygdx.game.Game2D.Entities.NPC.NPCMinigame1;
import com.mygdx.game.Game2D.World.GameMap;
import com.mygdx.game.Game2D.World.Minigame;

import java.util.ArrayList;

import static com.mygdx.game.Game2D.Screens.GameScreen.world;


public class Minigame1 extends GameMap implements Minigame {
    ArrayList<NPCMinigame1> npcs;
    public Minigame1(String mapName) {
        super(mapName);
    }

    @Override
    public void setNPCS() {
        npcs = new ArrayList<>();
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));
        npcs.add(new NPCMinigame1(1000));

//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.addNPC(new NPCMinigame1(1000));
//        npcManager.getNPCs().forEach(npc -> {
//            bodies.add(npc.boxBody);
//            System.out.println(world.getBodyCount());
//        });


        npcs.forEach(npc -> {
            bodies.add(npc.boxBody);
            System.out.println(world.getBodyCount());
        });
    }


    @Override
    public void onGameOver() {

    }

    @Override
    public void minigame() {
        npcs.forEach(NPCMinigame1::update);
    }
}