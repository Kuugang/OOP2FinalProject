package com.mygdx.game.Game2D.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Game2D.GameScreen;
import com.mygdx.game.Game2D.States.Direction;
import com.mygdx.game.Game2D.World.Maps.GLE202;
import com.mygdx.game.Game2D.World.Maps.Room;
import com.mygdx.game.ScreenConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Game2D.shape;
import static com.mygdx.game.Game2D.GameScreen.*;

public class MapManager {
    public static OrthogonalTiledMapRenderer tiledMapRenderer;
    Map<String, GameMap> maps = new HashMap<>();
    GameMap currentMap;
    public MapManager(){
        tiledMapRenderer = new OrthogonalTiledMapRenderer(null);
        maps.put("room", new Room().setMap("Game2D/Maps/HOUSE/HIS_HOUSE.tmx"));
        maps.put("GLE202", new GLE202().setMap("Game2D/Maps/GLE202 ROOM/GLE202.tmx"));
//        maps.put("GYM", new GLE202().setMap("Game2D/Maps/GYM/GYM.tmx"));
//        maps.put("FINAL_NGE_ROOM", new GLE202().setMap("Game2D/Maps/NGE ROOM/FINAL_NGE_ROOM.tmx"));
//        maps.put("LIBRARY", new GLE202().setMap("Game2D/Maps/LIBRARY/LIBRARY.tmx"));
//        maps.put("NGE_EXTERIOR", new GLE202().setMap("Game2D/Maps/EXTERIOR-20240511T113900Z-001/EXTERIOR/NGE_F.tmx"));
//        maps.put("TEST_GLE", new GLE202().setMap("Game2D/Maps/GLE202 ROOM/TEST_GLE.tmx"));
    }


    public void dispatchMap(String mapName, Vector2 playerPosition, Direction playerDirection) {
        //ADD DIALOGS
        GameMap map = maps.get(mapName);
        if (map != null) {
            float alpha = 1;

//            while(alpha >= 0){
//                alpha -= 0.000009F;
//                System.out.println(alpha);
//                shape.setProjectionMatrix(camera.combined);
//                shape.begin(ShapeRenderer.ShapeType.Filled);
//                shape.setColor(0, 0, 0, 0.5);
//                shape.rect(0, 0, ScreenConfig.screenWidth, ScreenConfig.screenHeight);
//                shape.end();
//            }

            Gdx.app.postRunnable(() -> {
                if (currentMap != null) {
                    currentMap.dispose();
                    tiledMapRenderer.getMap().dispose();
                }
                tiledMapRenderer.setMap(map.getTiledMap());
                currentMap = map;
                currentMap.setCollisions();
                currentMap.setExits();
                player.setPosition(playerPosition);
                player.setDirection(playerDirection);
            });
        }
    }


    public void update() {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        tiledMapRenderer.setView(camera);
        if (currentMap != null) {
            currentMap.update();
        }
        batch.end();
    }
}