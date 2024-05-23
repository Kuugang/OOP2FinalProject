package com.mygdx.game.Game2D.Threads;

import com.mygdx.game.Game2D.Entities.NPC.NPC;

public class NPCRunnable extends Thread{
    private final NPC npc;
    private boolean run;
    public NPCRunnable(NPC npc){
        this.npc = npc;
        run = true;
    }

    @Override
    public void run() {
        while(run){
            npc.move();
        }
    }

    public void stopThread(){
        run = false;
    }

    public NPC getNpc() {
        return npc;
    }
}
