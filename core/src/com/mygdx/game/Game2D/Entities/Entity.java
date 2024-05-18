package com.mygdx.game.Game2D.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;

import static com.mygdx.game.Game2D.Game2D.batch;

public class Entity implements InputProcessor {
    public float x, y;
    public Sprite sprite;
    public String map;
    public float speed;
    public Direction direction;
    protected Game2D game;

    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT, STAY;

        static public Direction getRandomNext() {
            return Direction.values()[MathUtils.random(Direction.values().length - 1)];
        }

        public Direction getOpposite() {
            if (this == LEFT) {
                return RIGHT;
            } else if (this == RIGHT) {
                return LEFT;
            } else if (this == UP) {
                return DOWN;
            } else {
                return UP;
            }
        }
    }

    public enum State {
        IDLE,
        WALKING;
    }
    public void setMap(String map){
        this.map = map;
    }

    float timePerCharacter = 0.1f; // Time (in seconds) between each character display
    float elapsedTime = 0f;
    int charactersToDisplay = 0;
    String dialogue;
    public boolean finishedDialoue = false;

    public void doDialogue(){
        if(!finishedDialoue){
            BitmapFont font = ResourceManager.pixel10;
            GlyphLayout layout = new GlyphLayout(font, dialogue);
            float textX = sprite.getX() + (sprite.getWidth() - layout.width) / 2;
            float textY = sprite.getY() + sprite.getHeight() + 20;

            float bgWidth = layout.width + 10;
            float bgHeight = layout.height + 10;

            batch.setColor(1, 1, 1, 0.9f);
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(1, 1, 1, 1);
            pixmap.drawPixel(0, 0);
            Texture texture = new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            batch.draw(texture, textX - 5, textY - 5, bgWidth, bgHeight);

            font.setColor(0, 0, 0, 1);

            String partialText = dialogue.substring(0, charactersToDisplay);
            font.draw(batch, partialText, textX, textY + 5);

            if (elapsedTime < dialogue.length() * timePerCharacter) {
                elapsedTime += Gdx.graphics.getDeltaTime();
                charactersToDisplay = (int) (elapsedTime / timePerCharacter);
            }else
                finishedDialoue = true;

            batch.setColor(Color.WHITE);
            font.setColor(Color.WHITE);
        }
    }

    public void setDialogue(String dialogue){
        this.dialogue = dialogue;
        finishedDialoue = false;
        elapsedTime = 0f;
        charactersToDisplay = 0;
    }

    public void stopDialogue(){
        finishedDialoue = true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Clicked");
        return screenX > sprite.getX() && screenX < sprite.getX() + sprite.getWidth() &&
                screenY > sprite.getY() && screenY < sprite.getY() + sprite.getHeight();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}