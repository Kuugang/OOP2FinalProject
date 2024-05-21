package com.mygdx.game.Game2D.World.Maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Entities.NPC.HouseNPC;
import com.mygdx.game.Game2D.World.CollisionType;
import com.mygdx.game.Game2D.World.MapExit;
import com.mygdx.game.Game2D.World.GameMap;
import com.mygdx.game.ScreenConfig;

import static com.mygdx.game.Game2D.Screens.GameScreen.*;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class Room extends GameMap {

    public Room(){
        npcs.add(new HouseNPC(3));
        npcs.forEach(npc -> inputMultiplexer.addProcessor(npc));
        player.setDialogue();
    }

    @Override
    public void update() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        player.render();
        for(Entity n : npcs){
            if(n instanceof HouseNPC){
                ((HouseNPC) n).render();
            }
        }

    }
}