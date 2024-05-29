package com.mygdx.game.Game2D.World.Maps.Minigames;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Entities.NPC.NPCMinigame1;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Utils.GameQueue;
import com.mygdx.game.Game2D.World.GameMap;
import com.mygdx.game.Game2D.World.Minigame;

public class MINIGAME1 extends GameMap implements Minigame {
    public static int health = 10;
    public int level;
    public MINIGAME1(String mapName, int level) {
        super(mapName);
        this.level = level;
    }
    
    @Override
    public void setNPCS() {
        new Thread(() -> {
            for(int i = 0; i < 50; i++) {
                NPCMinigame1 npcMinigame1 = new NPCMinigame1(1000, level);
                Gdx.app.postRunnable(() -> npcMinigame1.setTextureAtlas(ResourceManager.getRandomTA_NPC()));
                
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ignored){}

                GameQueue.add(() -> bodies.add(npcMinigame1.boxBody));

                npcManager.addNPC(npcMinigame1);
            }
        }).start();
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