package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.Entities.NPC.HouseNPC;
import com.mygdx.game.Game2D.World.GameMap;
import static com.mygdx.game.Game2D.Screens.GameScreen.*;

public class ROOM extends GameMap {
    public ROOM(String map){
        super(map);
    }

    public void setNPCS(){
        npcManager.addNPC(new HouseNPC(10000000)).addNPC(new HouseNPC(10000000)).addNPC(new HouseNPC(10000000));
        npcManager.getNPCs().forEach(npc -> bodies.add(npc.boxBody));
        player.setDialogue();
    }
}