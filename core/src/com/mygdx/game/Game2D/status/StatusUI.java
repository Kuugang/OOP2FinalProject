package com.mygdx.game.Game2D.status;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.Game2D.Manager.ResourceManager.STATUS_UI_SKIN;
public class StatusUI extends Window implements StatusSubject {
    private final Array<StatusObserver> observers;

    public StatusUI(){
        super("", STATUS_UI_SKIN);

        observers = new Array<>();


        //Add to layout
        defaults().expand().fill();

        //account for the title padding
        this.pad(30, 30, 10, 10);

        this.add();
        handlePortrait();
        this.row();

        setBackground(new Background());
        //this.debug();
        this.pack();
    }

    private void handlePortrait() {
        PlayerPortrait playerPortrait = new PlayerPortrait();
        playerPortrait.setSize(50, 50);
        this.add(playerPortrait).align(Align.right);
    }

    @Override
    public void addObserver(StatusObserver statusObserver) {
        observers.add(statusObserver);
    }

    @Override
    public void removeObserver(StatusObserver statusObserver) {
        observers.removeValue(statusObserver, true);
    }

    @Override
    public void removeAllObservers() {
        for(StatusObserver observer: observers) {
            observers.removeValue(observer, true);
        }
    }

    @Override
    public void notify(int value, StatusObserver.StatusEvent event) {
        for(StatusObserver observer: observers) {
            observer.onNotify(value, event);
        }
    }
}
