package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.Entities.NPC.HouseNPC;
import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.World.GameMap;

import java.util.List;
import java.util.stream.Collectors;

import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class ROOM extends GameMap {

    public ROOM(String map){
        super(map);
        npcManager.addNPC(new HouseNPC(10000000)).addNPC(new HouseNPC(10000000)).addNPC(new HouseNPC(10000000));
        npcManager.getNPCs().forEach(npc -> bodies.add(npc.boxBody));
        player.setDialogue();
    }

    @Override
    public void update() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        player.render();
        for(NPC n : npcManager.getNPCs()){
            n.render();
        }
    }
}