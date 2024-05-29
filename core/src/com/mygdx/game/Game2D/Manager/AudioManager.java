package com.mygdx.game.Game2D.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.Game2D.status.CurrentMusicDisplay;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    public static AudioManager instance;
    public static HashMap<String, Sound> sounds;
    public static HashMap<String, Music> musics;
    public static boolean buttonSound = false;
    private Music currentMusic;
    public CurrentMusicDisplay currentMusicDisplay;

    private AudioManager() {
        sounds = new HashMap<>();
        musics = new HashMap<>();
        loadSound();
        loadMusic();
        currentMusicDisplay = new CurrentMusicDisplay();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void loadMusic() {
        FileHandle[] files = Gdx.files.internal("media/musics").list();
        for (FileHandle file : files) {
            Music music = Gdx.audio.newMusic(file);
            musics.put(file.nameWithoutExtension(), music);
        }
    }

    public void loadSound() {
        FileHandle[] files = Gdx.files.internal("media/sounds").list();
        for (FileHandle file : files) {
            Sound sound = Gdx.audio.newSound(file);
            sounds.put(file.nameWithoutExtension(), sound);
        }
    }

    public void playSound(String name) {
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.play();
        }
    }

    public void playMusic(String name) {
        Music music = musics.get(name);
        if (music != null) {
            if (currentMusic != null) currentMusic.stop();
            currentMusic = music;
            currentMusic.setLooping(true);
            currentMusic.play();
            currentMusicDisplay.updateMusicLabel(name);
            currentMusicDisplay.startScrolling();
        }
    }

    public void playMusic(Music music) {
        if (music != null) {
            if (currentMusic != null) currentMusic.stop();
            currentMusic = music;
            currentMusic.setLooping(true);
            currentMusic.play();
            currentMusicDisplay.updateMusicLabel(getMusicTitle(music));
        }
    }

    public void stopMusic() {
        currentMusic.stop();
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

    public Music getMusic(String music) {
        return musics.get(music);
    }

    private String getMusicTitle(Music music) {
        for (Map.Entry<String, Music> entry : musics.entrySet()) {
            if (entry.getValue().equals(music)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public CurrentMusicDisplay getCurrentMusicDisplay(){
        return currentMusicDisplay;
    }
}
