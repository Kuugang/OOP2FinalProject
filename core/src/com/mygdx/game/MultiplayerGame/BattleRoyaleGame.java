package com.mygdx.game.MultiplayerGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MultiplayerGame.Network.GameClient;

public class BattleRoyaleGame extends Game {
	public static SpriteBatch batch;
	public static BitmapFont font;
	public static ShapeRenderer shape;
	public static GameClient gameClient;

	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		gameClient = new GameClient("127.0.0.1");
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
