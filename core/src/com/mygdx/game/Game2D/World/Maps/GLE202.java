package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.player;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class GLE202 extends GameMap {

    @Override
    public void setExits() {

    }

    @Override
    public void update() {
        int[] backgroundLayers = { 0, 1, 2, 3};
        int[] foregroundLayers = { 4 };

        player.render();
        tiledMapRenderer.render(backgroundLayers);
        tiledMapRenderer.render(foregroundLayers);
    }
}
