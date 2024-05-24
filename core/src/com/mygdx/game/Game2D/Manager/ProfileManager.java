package com.mygdx.game.Game2D.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.bullet.collision._btMprSupport_t;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Game2D.Entities.player.Player;
import com.mygdx.game.ScreenConfig;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileManager {
    private final Preferences preferences;
    private ArrayList<Player> profiles;
    private Player currentPlayer;

    public ProfileManager() {
        preferences = Gdx.app.getPreferences("OOP2FinalProject");

        String profilesJson = preferences.getString("profiles", null);

        if (profilesJson != null) {
            profiles = jsonToProfiles(profilesJson);
        } else {
            profiles = new ArrayList<>();
        }
    }

    public boolean addProfile(Player player){
        for (Player p : profiles) {
            if(p.username.equals(player.username)){
                return false;
            }
        }
        profiles.add(player);
        saveProfiles();
        return true;
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
        boolean exists = false;

        for (Player p: profiles) {
            if(Objects.equals(p.username, player.username)) {
                p.setPosition(new Vector2(player.getPosition().x, player.getPosition().y));
                p.setDirection(p.getDirection());
                p.setMap(player.map);
                exists = true;
                break;
            }
        }

        if (!exists) {
            profiles.add(player);
        }

        saveProfiles();
    }

    public void overwriteProfile(Player player){
        for(int i = 0; i < profiles.size(); i++){
            if(profiles.get(i).username.equals(player.username)){
                profiles.set(i, player);
            }
        }
        saveProfiles();
    }

    public ArrayList<Player> getProfiles(){
        return profiles;
    }

    public void loadProfile(String username){
        for (Player p : profiles) {
            if(Objects.equals(p.username, username)){
                currentPlayer = p;
                break;
            }
        }
    }

    public Player getCurrentPlayer() {
        currentPlayer.setCollision();
        return currentPlayer;
    }
}