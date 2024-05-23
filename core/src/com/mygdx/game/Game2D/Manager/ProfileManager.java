package com.mygdx.game.Game2D.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Game2D.Entities.player.Player;

import java.util.ArrayList;

//TODO SAVE ON DATABASE TOO
public class ProfileManager {
    private final Preferences preferences;
    ArrayList<Player> profiles;
    public ProfileManager() {
        preferences = Gdx.app.getPreferences("MyGamePreferences");


        String profilesJson = preferences.getString("profiles", null);

        if (profilesJson != null) {
            profiles = jsonToProfiles(profilesJson);
        } else {
            profiles = new ArrayList<>();
        }
    }

    public void saveProfiles() {
        Json jsonParser = new Json();
        String profilesJson = jsonParser.toJson(profiles);
        preferences.putString("profiles", profilesJson);
        preferences.flush();
    }

    private ArrayList<Player> jsonToProfiles(String json) {
        ArrayList<Player> profiles = new ArrayList<>();

        Json jsonParser = new Json();
        JsonValue root = new JsonReader().parse(json);

        for (JsonValue profileValue : root) {
            Player player = jsonParser.readValue(Player.class, profileValue);
            profiles.add(player);
        }

        return profiles;
    }

    public void saveProfile(Player player) {
        boolean test = false;
        for (Player p : profiles) {
            if (p.equals(player)) {
//                p.setMap(player.map);
//                p.setPosition(player.getPosition());
//                p.setDirection(player.getDirection());
                test = true;
                break;
            }
        }
        if(!test){
            profiles.add(player);
        }
        saveProfiles();
    }

    public Player getProfile(String username){
        for (Player p : profiles) {
            if (p.username.equals(username)) {
                return p;
            }
        }
        return null;
    }
}