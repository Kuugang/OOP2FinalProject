package com.mygdx.game.Game2D.World.Maps;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game2D.Entities.NPC.NPC1;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Utils.GameQueue;
import com.mygdx.game.Game2D.World.GameMap;

public class NGE_HALL extends GameMap {
    public NGE_HALL(String mapName) {
        super(mapName);
    }

    @Override
    public void setNPCS() {
        for(int i = 0; i < 10; i++){
            NPC1 npc = new NPC1((i % 2 == 0 ? 100 : 200));
            npc.setTextureAtlas(ResourceManager.getRandomTA_NPC());
            npc.setDialogues(ResourceManager.getRandomDialogues());
            npc.setPosition(new Vector2((i % 2 == 0 ? 33 : 35), 32 - i));
            npcManager.addNPC(npc);

            GameQueue.add(() -> bodies.add(npc.boxBody));
        }
    }
}