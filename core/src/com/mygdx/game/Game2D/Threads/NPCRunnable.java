package com.mygdx.game.Game2D.Threads;

import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Screens.GameScreen;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;

public class NPCRunnable extends Thread{
    private final ArrayList<NPC> npc;
    private boolean run;

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
        while(run) {
            synchronized (this.npc){
                for (NPC npc : npc)
                    if(!npc.setToStay)
                        npc.update();
            }
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