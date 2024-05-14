package com.mygdx.game.Game2D.status;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Must be continued for the dialogues
 */
public class ConversationUI extends Window implements StatusSubject {

    public ConversationUI(){
        super("", new Skin());//Incase for error, must upgrade to Java22+

    }
    @Override
    public void addObserver(StatusObserver statusObserver) {

    }

    @Override
    public void removeObserver(StatusObserver statusObserver) {

    }

    @Override
    public void removeAllObservers() {

    }

    @Override
    public void notify(int value, StatusObserver.StatusEvent event) {

    }
}
