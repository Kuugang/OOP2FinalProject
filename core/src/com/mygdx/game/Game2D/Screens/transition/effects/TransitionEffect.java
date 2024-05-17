package com.mygdx.game.Game2D.Screens.transition.effects;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Game2D.Screens.transition.TimeTransition;

public class TransitionEffect {

    private TimeTransition timeTransition;

    public TransitionEffect(float duration) {
        timeTransition = new TimeTransition();
        timeTransition.start(duration);
    }

    protected float getAlpha() {
        return timeTransition.get();
    }

    public void update(float delta) {
        timeTransition.update(delta);
    }

    public void render(Screen current, Screen next) {
        // Nothing
    }

    public boolean isFinished() {
        return timeTransition.isFinished();
    }

}
