package com.mygdx.game.Game2D.Manager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.HashMap;

public class SoundManager {
    private static HashMap<String, Sound> sounds;
    public String SOUND_PATH  = "sounds";
    public static boolean buttonSound = false;
    public SoundManager() {
        sounds = new HashMap<>();
        loadSound();
    }

    public void loadSound() {
        FileHandle[] files = Gdx.files.internal(SOUND_PATH).list();
        for (FileHandle file : files) {
                Sound sound = Gdx.audio.newSound(file);
                sounds.put(file.nameWithoutExtension(), sound);
        }


    }

    public static void playSound(String name) {
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.play();
        }
    }

    public void disposeSound(String name) {
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.dispose();
            sounds.remove(name);
        }
    }

    public void disposeAll() {
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        sounds.clear();
    }
}