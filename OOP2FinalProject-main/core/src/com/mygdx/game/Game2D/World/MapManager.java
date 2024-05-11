package com.mygdx.game.Game2D.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Game2D.World.Maps.GLE202;
import com.mygdx.game.Game2D.World.Maps.Room;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Game2D.GameScreen.camera;

public class MapManager {
    public OrthogonalTiledMapRenderer tiledMapRenderer;
    Map<String, GameMap> maps = new HashMap<>();
    GameMap currentMap;
    public MapManager(){
        tiledMapRenderer = new OrthogonalTiledMapRenderer(null);
        maps.put("room", new Room().setMap("Game2D/Maps/HOUSE/HIS_HOUSE.tmx"));
//        maps.put("GLE202", new GLE202().setMap("Game2D/Maps/GLE202 ROOM/GLE202_ROOM.tmx"));
//        maps.put("GYM", new GLE202().setMap("Game2D/Maps/GYM/GYM.tmx"));
        maps.put("FINAL_NGE_ROOM", new GLE202().setMap("Game2D/Maps/NGE ROOM/FINAL_NGE_ROOM.tmx"));
        maps.put("LIBRARY", new GLE202().setMap("Game2D/Maps/LIBRARY/LIBRARY.tmx"));
//        maps.put("NGE_EXTERIOR", new GLE202().setMap("Game2D/Maps/EXTERIOR-20240511T113900Z-001/EXTERIOR/NGE_F.tmx"));
        maps.put("TEST_GLE", new GLE202().setMap("Game2D/Maps/GLE202 ROOM/TEST_GLE.tmx"));
    }

    public void dispatchMap(String mapName){
        GameMap map = maps.get(mapName);

        if(map != null){
            Gdx.app.postRunnable(() -> {
                if(currentMap != null) {
                    currentMap.dispose();
                    tiledMapRenderer.getMap().dispose();
                }
                tiledMapRenderer.setMap(map.getTiledMap());
                currentMap = map;
                map.setCollisions();
            });
        }
    }

    public void update(){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
//        currentMap.update();
    }
}