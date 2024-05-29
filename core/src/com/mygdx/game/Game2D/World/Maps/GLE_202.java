package com.mygdx.game.Game2D.World.Maps;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.NPC1;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Utils.GameQueue;
import com.mygdx.game.Game2D.Utils.NPC_NPC_Bridge;
import com.mygdx.game.Game2D.World.GameMap;

import java.util.ArrayList;
import java.util.Arrays;

public class GLE_202 extends GameMap {
    NPC_NPC_Bridge npcNpcBridge;

    public GLE_202(String mapName) {
        super(mapName);
    }

    @Override
    public void setNPCS() {
        NPC1 npc1 = new NPC1(100);
        npc1.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc1.setToStay(Entity.Direction.DOWN);
        npc1.setPosition(new Vector2(2, 10));
        npc1.setDialogues(new ArrayList<>(
                Arrays.asList(
                        "Don't you have something better to do?",
                        "I don't have time for this right now.",
                        "Why are you even here?",
                        "Leave me alone.",
                        "I don't want to talk to you.",
                        "Go bother someone else.",
                        "I have nothing to say to you.",
                        "You’re not worth my time.",
                        "Just go away.",
                        "Stop wasting my time.",
                        "I can't deal with you right now.",
                        "What do you want this time?",
                        "I don’t care about your problems.",
                        "Can’t you take a hint?",
                        "Do you ever get tired of being annoying?",
                        "Your presence is not welcome here.",
                        "Why don't you just disappear?",
                        "I have more important things to do.",
                        "Talking to you is pointless.",
                        "I’m not interested in anything you have to say.",
                        "Why do you always have to make things worse?"
                        )
                )
        );

        npcManager.addNPC(npc1);

        NPC1 npc2 = new NPC1(100);
        npc2.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc2.setToStay(Entity.Direction.UP);
        npc2.setPosition(new Vector2(2, 8));
        npc2.setDialogues(new ArrayList<>(
                Arrays.asList(
                        "Don't you have something better to do?",
                        "I don't have time for this right now.",
                        "Why are you even here?",
                        "Leave me alone.",
                        "I don't want to talk to you.",
                        "Go bother someone else.",
                        "I have nothing to say to you.",
                        "You’re not worth my time.",
                        "Just go away.",
                        "Stop wasting my time.",
                        "I can't deal with you right now.",
                        "What do you want this time?",
                        "I don’t care about your problems.",
                        "Can’t you take a hint?",
                        "Do you ever get tired of being annoying?",
                        "Your presence is not welcome here.",
                        "Why don't you just disappear?",
                        "I have more important things to do.",
                        "Talking to you is pointless.",
                        "I’m not interested in anything you have to say.",
                        "Why do you always have to make things worse?"
                        )
                )
        );
        npcManager.addNPC(npc2);

        NPC1 npc3 = new NPC1(100);
        npc3.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc3.setToStay(Entity.Direction.LEFT);
        npc3.setPosition(new Vector2(20, 9));
        npc3.setDialogues(new ArrayList<>(Arrays.asList(
                "Hey, it's great to see you!",
                "How have you been?",
                "What’s up?",
                "Long time no see!",
                "I've been thinking about you.",
                "You always brighten my day.",
                "Do you have a minute to chat?",
                "I was hoping to run into you.",
                "Let's catch up!",
                "What's new with you?",
                "I'm so glad you're here.",
                "You always know how to make me smile.",
                "What’s been going on?",
                "Want to grab a coffee?",
                "I've missed our conversations.",
                "You have the best stories.",
                "How's everything going on your end?",
                "Tell me about your day.",
                "I could use your advice on something.",
                "It's always a pleasure talking to you."
        )));
        npcManager.addNPC(npc3);


        NPC1 npc4 = new NPC1(100);
        npc4.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc4.setPosition(new Vector2(15, 9));
        npcManager.addNPC(npc4);

        NPC1 npc5 = new NPC1(100);
        npc5.setTextureAtlas(ResourceManager.getRandomTA_NPC());
        npc5.setPosition(new Vector2(8, 9));
        npcManager.addNPC(npc5);
/*        npc1.setDialogues();


        npcNpcBridge = new NPC_NPC_Bridge(npc1, npc2);
        npcNpcBridge.run();*/


        GameQueue.add(() -> {
            bodies.add(npc1.boxBody);
            bodies.add(npc2.boxBody);
            bodies.add(npc3.boxBody);
            bodies.add(npc4.boxBody);
            bodies.add(npc5.boxBody);
        });

    }
}