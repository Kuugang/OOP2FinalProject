package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.HouseNPC;
import com.mygdx.game.Game2D.Manager.InputManager;
import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class ROOM extends GameMap {

    public ROOM(){
        npcManager.addNPC(new HouseNPC(3));
        npcManager.getNPCs().forEach(npc -> InputManager.inputMultiplexer.addProcessor(npc));
        player.setDialogue();
    }

    @Override
    public void update() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        player.render();
        for(Entity n : npcManager.getNPCs()){
            if(n instanceof HouseNPC){
                ((HouseNPC) n).render();
            }
        }

    }
}