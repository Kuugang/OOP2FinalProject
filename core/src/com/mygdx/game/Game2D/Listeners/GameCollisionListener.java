package com.mygdx.game.Game2D.Listeners;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Screens.BaseScreen;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.Game2D.World.MapExit;
import com.mygdx.game.Game2D.World.MapManager;
import com.mygdx.game.ScreenConfig;

public class GameCollisionListener implements ContactListener {
    Game2D game;
    BaseScreen screen;
    MapManager mapManager;
    public GameCollisionListener(Game2D game, BaseScreen screen, MapManager mapManager){
        this.game = game;
        this.screen = screen;
        this.mapManager = mapManager;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = fixtureA.getUserData();
        Object objectB = fixtureB.getUserData();
        if(fixtureA.getUserData() == null || fixtureB.getUserData() == null)return;

        if(objectA.equals("player")){
            if(objectB instanceof MapExit){
                mapManager.dispatchMap("GLE202", new Vector2(3 * ScreenConfig.originalTileSize, ScreenConfig.originalTileSize), Direction.UP);
            }
        }

        if(objectA instanceof  NPC){
            if(objectB == "COLLISION"){
                ((NPC)objectA).newMovement();
                ((NPC)objectA).movement.setCurrentDirection();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
