package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.HouseNPC;
import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class Room extends GameMap {

    public Room(){
        npcManager.addNPC(new HouseNPC(200));
        /*npcManager.addNPC(new HouseNPC(200)).addNPC(new HouseNPC(100)).addNPC(new HouseNPC(50))
                .addNPC(new HouseNPC(25)).addNPC(new HouseNPC(20));*/
        npcManager.getNPCs().forEach(npc -> inputMultiplexer.addProcessor(npc));
        player.setDialogue();
    }

    @Override
    public void update() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        for(Entity n : npcManager.getNPCs()){
            if(n instanceof HouseNPC){
                ((HouseNPC) n).render();
            }
        }
        player.render();
    }
}