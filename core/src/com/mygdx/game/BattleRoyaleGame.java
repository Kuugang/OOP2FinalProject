package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Network.GameClient;

public class BattleRoyaleGame extends Game {
	SpriteBatch batch;
	BitmapFont font;
	public static GameClient gameClient;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		gameClient = new GameClient("hosting-ensures.gl.at.ply.gg");
		gameClient.start();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
