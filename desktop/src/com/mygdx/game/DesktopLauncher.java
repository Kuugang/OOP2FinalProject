package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.BattleRoyaleGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Battle Royale Game");
		config.setWindowedMode(ScreenConfig.screenWidth, ScreenConfig.screenHeight);
		new Lwjgl3Application(new BattleRoyaleGame(), config);
	}
}
