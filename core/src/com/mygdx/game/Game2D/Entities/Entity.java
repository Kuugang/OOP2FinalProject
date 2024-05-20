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
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Game2D.Entities.NPC.NPC;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;

import static com.mygdx.game.Game2D.Game2D.batch;
import static com.mygdx.game.Game2D.Screens.GameScreen.player;

public class Entity implements InputProcessor {
    public float x, y;
    public Sprite sprite;
    public String map;
    public float speed;
    public Direction direction;
    protected Game2D game;
    public Body boxBody;

    public Sprite getSprite() {
        return sprite;
    }

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

        public static Direction fromString(String direction) {
            if (direction != null) {
                try {
                    return Direction.valueOf(direction.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid direction: " + direction);
                }
            }
            return null;
        }
    }

    public enum State {
        IDLE,
        WALKING;
    }
    public void setMap(String map){
        this.map = map;
    }

    private float timePerCharacter = 0.1f; // Time (in seconds) between each character display
    private float elapsedTime = 0f;
    private int charactersToDisplay = 0;
    private String dialogue;
    public boolean finishedDialogue = true;

    public void doDialogue(){
        if(!finishedDialogue) {
            batch.begin();
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

            if (elapsedTime < dialogue.length() * timePerCharacter + 3) {
                elapsedTime += Gdx.graphics.getDeltaTime();
                charactersToDisplay = (int) (elapsedTime / timePerCharacter);
                if(charactersToDisplay > dialogue.length())
                    charactersToDisplay = dialogue.length();

                if(this instanceof NPC)
                    redirection((NPC) this, dialogue.length() - charactersToDisplay + 1);
            }else {
                finishedDialogue = true;
                if(this instanceof NPC){
                    ((NPC) this).newMovement();
                    ((NPC)this).movement.setCurrentDirection();
                }
            }

            batch.setColor(Color.WHITE);
            font.setColor(Color.WHITE);
            batch.end();
        }
    }

    public void redirection(NPC npc, int length){
        float npcX = npc.getX(), npcY = npc.getY();
        float playerX = player.getX(), playerY = player.getY();

        if(playerY > npcY) {
            if(npc.direction == Direction.UP)
                return;
            npc.direction = Direction.UP;
        }else if(playerX < npcX) {
            if(npc.direction == Direction.LEFT)
                return;
            npc.direction = Direction.LEFT;
        }else if(playerX > npcX) {
            if(npc.direction == Direction.RIGHT)
                return;
            npc.direction = Direction.RIGHT;
        }else{
            if(npc.direction == Direction.DOWN)
                return;
            npc.direction = Direction.DOWN;
        }

        npc.setToStay(length);
    }

    public Entity setDialogue(String dialogue){
        this.dialogue = dialogue;
        finishedDialogue = false;
        charactersToDisplay = 0;
        elapsedTime = 0f;

        return this;
    }

    public float getX(){
        return boxBody == null ? 0 : boxBody.getPosition().x;
    }

    public float getY(){
        return boxBody == null ? 0 : boxBody.getPosition().y;
    }

    public void stopDialogue(){
        finishedDialogue = true;
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