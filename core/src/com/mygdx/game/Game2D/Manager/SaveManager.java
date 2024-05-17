package com.mygdx.game.Game2D.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

//TODO SAVE ON DATABASE TOO
public class SaveManager {
    private Preferences preferences;

    public SaveManager() {
        preferences = Gdx.app.getPreferences("MyGamePreferences");
    }

    // Save player position
    public void savePlayerPosition(float x, float y) {
        preferences.putFloat("player_x", x);
        preferences.putFloat("player_y", y);
        preferences.flush();
    }



    // Load player position
    public float[] loadPlayerPosition() {
        float x = preferences.getFloat("player_x", 0);
        float y = preferences.getFloat("player_y", 0);
        return new float[]{x, y};
    }

    // Save score
    public void saveUserDetails(String name) {
        preferences.putString("name", name);
        preferences.flush();
    }


    public void saveMap(String map) {
        preferences.putString("map", map);
        preferences.flush();
    }

    // Load score
    public int loadScore() {
        return preferences.getInteger("score", 0);
    }

    // Save level
    public void saveLevel(int level) {
        preferences.putInteger("level", level);
        preferences.flush();
    }

    // Load level
    public int loadLevel() {
        return preferences.getInteger("level", 1);
    }

    public void clearPreferences() {
        preferences.clear();
        preferences.flush();
    }
}
