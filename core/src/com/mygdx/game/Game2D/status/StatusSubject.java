package com.mygdx.game.Game2D.status;


public interface StatusSubject {
    void addObserver(StatusObserver statusObserver);
    void removeObserver(StatusObserver statusObserver);
    void removeAllObservers();
    void notify(final int value, StatusObserver.StatusEvent event);
}
