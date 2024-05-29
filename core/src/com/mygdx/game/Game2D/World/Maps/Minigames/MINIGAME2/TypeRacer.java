package com.mygdx.game.Game2D.World.Maps.Minigames.MINIGAME2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Game2D.Manager.AudioManager;
import com.mygdx.game.Game2D.Manager.ResourceManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TypeRacer extends Game implements InputProcessor {


    private static final List<Word> possibleWords = new ArrayList<>();
    final Random random = new Random();
    SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont uifont;
    private List<Word> words = new ArrayList<>();
    private String currentWord;
    private int lives;
    private long points;
    private LocalTime startTime;
    GlyphLayout layout = new GlyphLayout();
    private Texture texture;
    private double blinkTime;
    public float speed = 3;
    public GameOver gameover = null;
    //    private ExitScreen exitScreen;
    @Override
    public void create() {
        texture = new Texture("atlas/UI/TypeRacerBackground.png");
        initializeWords();
        initializeStage();

        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                addWord();
                if (speed >= 1) {
                    speed -= 0.1F;
                }
                System.out.println("Speed" + speed);

                this.cancel();

                Timer.schedule(this, 2, .5f);
            }
        };

        Timer.schedule(task, 2, 1);
    }


    public void initializeStage() {
        batch = new SpriteBatch();
        ResourceManager resourceManager = new ResourceManager();
        uifont = new BitmapFont();
        font = ResourceManager.pixel10;
        lives = 3;
        points = 0;
        currentWord = "";
        startTime = LocalTime.now();
        Gdx.input.setInputProcessor(this);


    }

    public void addWord() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(possibleWords.size());
        Word word = possibleWords.get(randomIndex);

        float wordWidth = getWordWidth(word.getText());

        int maxWidth = Gdx.graphics.getWidth() - 40;
        int x = rand.nextInt(maxWidth - (int)wordWidth) + 20;

        word.setPosition(new Vector2(x, Gdx.graphics.getHeight() - 70));

        word.speed = rand.nextFloat() * 50 + 50; // Random speed between 50 and 100
        words.add(word);
    }

    private float getWordWidth(String text){
        GlyphLayout layout = new GlyphLayout(font, text);
        return layout.width;
    }


    public void initializeWords() {

        try (BufferedReader reader = new BufferedReader(new FileReader("assets/words/words.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                Word words1 = new Word(line, 0);
                possibleWords.add(words1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(words.size());

    }

    @Override
    public void render() {


        blinkTime += Gdx.graphics.getDeltaTime();
        if (blinkTime > 1) { // Reset after one second
            blinkTime = 0;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        if (lives < 1) {
            if (gameover==null){
                gameover = new GameOver(points, Duration.between(startTime, LocalTime.now()));
            }
            setScreen(gameover);
            return;
        }

        super.render();
        batch.begin();

        uifont.getData().setScale(1.5f);
        uifont.setColor(Color.BLACK);
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uifont.draw(batch, "Lives: " + lives, 10, Gdx.graphics.getHeight() - 10);
        uifont.draw(batch, "Current Word: " + currentWord, 10, Gdx.graphics.getHeight() - 40);
        GlyphLayout pointsLayout = new GlyphLayout();
        String pointsText = "Points: " + points;
        pointsLayout.setText(uifont, pointsText);

        float pointsX = Gdx.graphics.getWidth() - pointsLayout.width - 10; // 10 is the margin from the right edge of the screen
        uifont.draw(batch, pointsText, pointsX, Gdx.graphics.getHeight() - 10);

        font.getData().setScale(2.0f);
        Iterator<Word> iterator = words.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            try {
                word.updatePosition(Gdx.graphics.getDeltaTime());


                font.setColor(Color.BLACK); // Set the color to red
                String highlightedPart = word.getText().substring(0, word.getHighlightedCharacters());
                layout.setText(font, highlightedPart);
                float highlightedWidth = layout.width;
                font.draw(batch, highlightedPart, word.getPosition().x, word.getPosition().y);

                // Draw the rest of the word
                font.setColor(Color.GREEN); // Set the color to white
                String restOfWord = word.getText().substring(word.getHighlightedCharacters());
                font.draw(batch, restOfWord, word.getPosition().x + highlightedWidth, word.getPosition().y);


            } catch (Exception e) {
                AudioManager.getInstance().playSound("error");
                lives--;
                e.printStackTrace();
                iterator.remove(); // Remove the word from the list
            }
        }
        if (lives <= 0) {
            font.draw(batch, "Game Over", (float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        }

        batch.end();
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
        if (character == '\n') {
            return false;
        }
        if (character == '\b' && currentWord.length() > 0) { // If backspace is pressed and currentWord is not empty
            currentWord = currentWord.substring(0, currentWord.length() - 1); // Remove the last character
        } else if (character != '\b') { // If a printable character is typed
            currentWord += character;
        }

        Iterator<Word> iterator = words.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getText().startsWith(currentWord)) {
                word.setHighlightedCharacters(currentWord.length());
            }
            if (word.getText().equals(currentWord)) {
                AudioManager.getInstance().playSound("correct");
                points += currentWord.length();
                iterator.remove();
                currentWord = "";
                break;
            }
        }
        System.out.println(currentWord);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
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
