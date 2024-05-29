package com.mygdx.game.Game2D.World.Maps.Minigames;

import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Entities.NPC.NPCMinigame1;
import com.mygdx.game.Game2D.World.GameMap;
import com.mygdx.game.Game2D.World.Minigame;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.mygdx.game.Game2D.Screens.GameScreen.player;


public class MINIGAME1 extends GameMap implements Minigame {
    public static int health = 10;
    public int level;
    public MINIGAME1(String mapName, int level) {
        super(mapName);
        this.level = level;
    }

    @Override
    public void setNPCS() {
        for(int i = 0; i < 14; i++) {
            NPCMinigame1 npcMinigame1 = new NPCMinigame1(1000, level);
            npcMinigame1.setTextureAtlas("atlas/leo.atlas");
            npcManager.addNPC(npcMinigame1);
        }

        npcManager.getNPCs().forEach(npc -> bodies.add(npc.boxBody));
    }

    @Override
    public void onGameOver() {

    }

    @Override
    public void minigame() {
        npcManager.getNPCs().forEach(NPC::update);
        if(health <= 0){
            onGameOver();
        }
    }
}