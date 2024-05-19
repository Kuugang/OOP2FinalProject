package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.player;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class NGE_CR extends GameMap {

    @Override
    public void setExits() {

    }

    @Override
    public void update() {
        int[] backgroundLayers = { 0, 1 };
        int[] foregroundLayers = { 2 };

        tiledMapRenderer.render(backgroundLayers);
        player.render();
        tiledMapRenderer.render(foregroundLayers);
    }
}
