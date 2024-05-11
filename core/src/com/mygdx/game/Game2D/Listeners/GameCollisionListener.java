package com.mygdx.game.Game2D.Listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Game2D.GameScreen;

import java.util.AbstractMap;
import java.util.Objects;

public class GameCollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = fixtureA.getUserData();
        Object objectB = fixtureB.getUserData();

        if(fixtureA.getUserData() == null || fixtureB.getUserData() == null)return;
        if (objectA.equals("player") || objectB.equals("player")) {
            if (objectA instanceof AbstractMap.SimpleEntry<?,?> || objectB instanceof AbstractMap.SimpleEntry<?,?>) {
                AbstractMap.SimpleEntry<String, String> entry = (AbstractMap.SimpleEntry<String, String>) objectB;
                if(Objects.equals(entry.getKey(), "exit")){
                    GameScreen.mapManager.dispatchMap(entry.getValue());
                }
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
