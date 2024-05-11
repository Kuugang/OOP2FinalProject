package com.mygdx.game.Game2D.Listeners;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.GameScreen;
import com.mygdx.game.Game2D.World.ExitMap;

import java.util.AbstractMap;
import java.util.Objects;
import java.util.Vector;

public class GameCollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = fixtureA.getUserData();
        Object objectB = fixtureB.getUserData();

        if(fixtureA.getUserData() == null || fixtureB.getUserData() == null)return;

        if(objectA.equals("player")){
            if(objectB instanceof ExitMap exitMap){
                GameScreen.mapManager.dispatchMap(exitMap.nextMap, exitMap.playerPosition, exitMap.playerDirection);
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
