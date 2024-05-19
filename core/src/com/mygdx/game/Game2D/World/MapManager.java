package com.mygdx.game.Game2D.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game2D.Entities.PlayerMP;
import com.mygdx.game.Game2D.Screens.transition.effects.FadeOutTransitionEffect;
import com.mygdx.game.Game2D.Screens.transition.effects.TransitionEffect;
import com.mygdx.game.Game2D.World.Maps.GLE202;
import com.mygdx.game.Game2D.World.Maps.GLE_CR;
import com.mygdx.game.Game2D.World.Maps.NGE_CR;
import com.mygdx.game.Game2D.World.Maps.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Screens.GameScreen.*;

public class MapManager {
    public static OrthogonalTiledMapRenderer tiledMapRenderer;
    private final Map<String, GameMap> maps = new HashMap<>();
    public static Map<String, PlayerMP> otherPlayers = new HashMap<>();
    public GameMap currentMap;

    public MapManager(){
        tiledMapRenderer = new OrthogonalTiledMapRenderer(null);
        maps.put("ROOM", new Room().setMap("Game2D/Maps/HOUSE/HIS_HOUSE.tmx").setMapName("ROOM"));
        maps.put("GLE202", new GLE202().setMap("Game2D/Maps/GLE202 ROOM/GLE202.tmx").setMapName("GLE202"));
//        maps.put("GYM", new GLE202().setMap("Game2D/Maps/GYM/GYM.tmx"));
//        maps.put("FINAL_NGE_ROOM", new GLE202().setMap("Game2D/Maps/NGE ROOM/FINAL_NGE_ROOM.tmx"));
//        maps.put("LIBRARY", new GLE202().setMap("Game2D/Maps/LIBRARY/LIBRARY.tmx"));
//        maps.put("NGE_EXTERIOR", new GLE202().setMap("Game2D/Maps/EXTERIOR-20240511T113900Z-001/EXTERIOR/NGE_F.tmx"));
//        maps.put("TEST_GLE", new GLE202().setMap("Game2D/Maps/GLE202 ROOM/TEST_GLE.tmx"));
        maps.put("GLE_CR", new GLE_CR().setMap("Game2D/Maps/COMMON CR/GLE_CR.tmx").setMapName("GLE_CR"));
        maps.put("NGE_CR", new NGE_CR().setMap("Game2D/Maps/COMMON CR/NGE_CR.tmx").setMapName("NGE_CR"));
    }

    public void dispatchMap(MapExit mapExit) {
        //ADD DIALOGS
        GameMap map = maps.get(mapExit.nextMap);
        if (map != null) {

            Gdx.app.postRunnable(() -> {
                if (currentMap != null) {
                    currentMap.dispose();
                    tiledMapRenderer.getMap().dispose();
                }

                player.setDirection(mapExit.playerDirection);
                player.setMap(mapExit.nextMap);
                player.setPosition(mapExit.playerPosition.x, mapExit.playerPosition.y);

                ArrayList<TransitionEffect> effects = new ArrayList<>();
                effects.add(new FadeOutTransitionEffect(1f));

                tiledMapRenderer.setMap(map.getTiledMap());
                currentMap = map;
                currentMap.setCollisions();
                currentMap.setExits();
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

    public GameMap getMap(String map){
        return maps.get(map);
    }
}