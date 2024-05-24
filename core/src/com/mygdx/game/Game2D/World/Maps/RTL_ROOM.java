package com.mygdx.game.Game2D.World.Maps;

import com.mygdx.game.Game2D.World.GameMap;

import static com.mygdx.game.Game2D.Screens.GameScreen.camera;
import static com.mygdx.game.Game2D.Screens.GameScreen.player;
import static com.mygdx.game.Game2D.World.MapManager.tiledMapRenderer;

public class RTL_ROOM extends GameMap {
    @Override
    public void update() {
        int[] backgroundLayers = { 0 };
        int[] foregroundLayers = { 1 };

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        player.render();
    }
}