package com.mygdx.game.Game2D.status;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Game2D.World.World;

import static com.mygdx.game.Game2D.Manager.ResourceManager.STATUS_UI_SKIN;

public class StatusUI extends Window implements StatusSubject {
    private final Array<StatusObserver> observers;

    public StatusUI(){
        super("", STATUS_UI_SKIN);

        observers = new Array<>();

        // Add to layout
        defaults().expand().fill();

        // Account for the title padding
        this.pad(30, 30, 10, 10);

        Table portraitTable = new Table();
        handlePortrait();
        this.add(portraitTable).align(Align.left).padRight(10);

        Table infoTable = new Table();
        handleName(infoTable);
        handleDay(infoTable);
        this.add(infoTable).align(Align.left).padLeft(10);

        this.row();

        setBackground(new Background());
//        this.debug();
        this.pack();
    }

    private void handlePortrait() {
        PlayerPortrait playerPortrait = new PlayerPortrait();
        playerPortrait.setSize(50, 50);
        playerPortrait.setPosition(0, 0);
        this.add(playerPortrait).align(Align.right);
    }

    private void handleName(Table table) {
        Label nameLabel = new Label(World.username + " McLuffy", STATUS_UI_SKIN);
        nameLabel.setColor(Color.BLACK);
        table.add(nameLabel).align(Align.left).spaceBottom(10).row();
    }

    private void handleDay(Table table) {
        Label dayLabel = new Label("Day: " + World.day, STATUS_UI_SKIN);
        dayLabel.setColor(Color.BLACK);
        table.add(dayLabel).align(Align.left).spaceBottom(10).row();
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
