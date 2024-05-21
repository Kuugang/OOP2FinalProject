package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.player;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class RTL_ACCOUNTING extends GameMap {

    @Override
    public void setExits() {

    }

    @Override
    public void update() {
        int[] backgroundLayers = { 0};
        int[] foregroundLayers = { };

        tiledMapRenderer.render(backgroundLayers);
        player.render();
        tiledMapRenderer.render(foregroundLayers);
    }
}
