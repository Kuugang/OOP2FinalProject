package com.mygdx.game.Game2D.World.Maps;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game2D.Dialogues.Dialogues;
import com.mygdx.game.Game2D.Entities.NPC.NPC1;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Utils.GameQueue;
import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.camera;
import static com.mygdx.game.Game2D.Screens.GameScreen.player;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class NGE_ROOM extends GameMap {

    public NGE_ROOM(String mapName) {
        super(mapName);
    }

    @Override
    public void setNPCS() {
        NPC1 npc1 = new NPC1(100);
        npc1.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc1.setDialogues(Dialogues.dialogues2);
        npc1.setPosition(new Vector2(7, 3));
        npcManager.addNPC(npc1);

/*        NPC1 npc2 = new NPC1(200);
        npc2.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc2.setDialogues(Dialogues.dialogues2);
        npc2.setPosition(new Vector2(12, 15));
        npcManager.addNPC(npc2);*/

        GameQueue.add(() -> {
            bodies.add(npc1.boxBody);
//            bodies.add(npc2.boxBody);
        });
    }
}
