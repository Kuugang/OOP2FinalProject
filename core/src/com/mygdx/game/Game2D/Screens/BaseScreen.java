package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Manager.AudioManager;
import com.mygdx.game.Game2D.Screens.transition.effects.TransitionEffect;
import com.mygdx.game.Game2D.status.CurrentMusicDisplay;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Game2D.Manager.ResourceManager.BUTTONS_TEXTURE_ATLAS;


public class BaseScreen implements Screen{
    protected final Game2D game;
    protected ResourceManager resourceManager;
    protected OrthographicCamera gameCam;
    protected OrthographicCamera battleCam;
    protected Viewport viewport;
    protected Stage stage;
    CurrentMusicDisplay currentMusicDisplay;

    public BaseScreen(Game2D game) {
        this.game = game;
        this.resourceManager = Game2D.resourceManager;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Initialize the stage

        currentMusicDisplay = AudioManager.getInstance().getCurrentMusicDisplay();
    }

    public void setScreenWithTransition(BaseScreen current, BaseScreen next, List<TransitionEffect> transitionEffect) {
        ArrayList<TransitionEffect> effects = new ArrayList<>(transitionEffect);

        Screen transitionScreen = new TransitionScreen(game, current, next, effects);
        game.setScreen(transitionScreen);
    }

    public TextButton createButton(String buttonName) {
        TextureRegion[][] playButtons = resourceManager.button;

        BitmapFont pixel10 =  ResourceManager.pixel10;

        TextureRegionDrawable imageUp = new TextureRegionDrawable(playButtons[0][0]);
        TextureRegionDrawable imageDown = new TextureRegionDrawable(playButtons[1][0]);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(imageUp, imageDown, null, pixel10);
        TextButton button = new TextButton(buttonName, buttonStyle);

        //Sound for hover
        button.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //Hover sound
                if(!AudioManager.buttonSound)
                {
                    AudioManager.getInstance().playSound("hover");
                }
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //Hover sound
                AudioManager.buttonSound = false;
            }
        });

        button.getLabel().setColor(new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1));

        return button;
    }

    public ImageButton createImageButton(String buttonName, Table table) {
            TextureRegion buttonUp = BUTTONS_TEXTURE_ATLAS.findRegion(buttonName);

            if (buttonUp == null) {
                Gdx.app.error("createImageButton", "Region not found for button: " + buttonName);
                return null;
            }

            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.imageUp = new TextureRegionDrawable(buttonUp);

            ImageButton button = new ImageButton(buttonStyle);
            button.setTransform(true);

            button.setOrigin(button.getWidth() / 2, button.getHeight() / 2);

            button.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    // Hover sound
                    if (!AudioManager.buttonSound) {
                        AudioManager.getInstance().playSound("hover");
                    }
                    button.setScale(1.1F);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    AudioManager.buttonSound = false;
                    button.setScale(1.0F);
                }
            });

            table.add(button).pad(10);

            return button;
    }

    public Table createTable() {
        Table table = new Table();
        table.setBounds(0,0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setDebug(true);
        return table;
    }

    public Table createTable(float width, float height)
    {
        Table table = new Table();
        table.setBounds(0,0, width, height);
        return table;
    }

    @Override
    public void show() {
        // Nothing
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(delta);
        stage.draw();
        currentMusicDisplay.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Nothing
    }

    @Override
    public void pause() {
        // Nothing
    }

    @Override
    public void resume() {
        // Nothing
    }

    @Override
    public void hide() {
        // Nothing
    }

    @Override
    public void dispose() {
//        stage.dispose();
    }
    public Stage getStage() {
        return stage;
    }
}
