package com.mygdx.game.Game2D.Threads;

import com.mygdx.game.Game2D.Entities.NPC.NPC;

import java.util.ArrayList;

public class NPCRunnable extends Thread{
    private final ArrayList<NPC> npc;
    private boolean run;

    public NPCRunnable(NPC npc){
        this.npc = new ArrayList<>();
        this.npc.add(npc);
        run = true;
    }

    public NPCRunnable(){
        npc = new ArrayList<>();
        run = true;
    }

    public void addNPC(NPC npc){
        synchronized (this.npc){
            this.npc.add(npc);
        }
    }

    @Override
    public void run() {
        while(run)
            synchronized (this.npc){
                for (NPC npc : npc)
                    if(!npc.setToStay)
                        npc.move();
            }
    }

    public void stopThread(){
        run = false;
    }

    public ArrayList<NPC> getNpc() {
        return npc;
    }

    public int getSize(){
        return npc.size();
    }
}