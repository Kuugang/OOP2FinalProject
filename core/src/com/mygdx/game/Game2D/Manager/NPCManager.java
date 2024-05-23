package com.mygdx.game.Game2D.Manager;

import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Threads.NPCRunnable;

import java.util.ArrayList;

/**
    This class will be for managing npc movements with threads
 */
public class NPCManager {
    private final ArrayList<NPCRunnable> npcRunnables;
    public NPCManager() {
        npcRunnables = new ArrayList<>();
    }

    public NPCManager addNPC(NPC npc) {
        npcRunnables.add(new NPCRunnable(npc));
        npcRunnables.getLast().start();
        return this;
    }

    /**
     * Must be called whenever this manager is not used anymore to properly stop all the threads
     */
    public void clear(){
        while(!npcRunnables.isEmpty()){
            npcRunnables.getLast().stopThread();
            npcRunnables.removeLast();
        }
    }

    public ArrayList<NPC> getNPCs(){
        return npcRunnables.stream().map(NPCRunnable::getNpc).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
