package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.AudioManager;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Screens.transition.effects.FadeOutTransitionEffect;
import com.mygdx.game.Game2D.Screens.transition.effects.TransitionEffect;

import java.util.ArrayList;

import static com.mygdx.game.Game2D.Game2D.profileManager;
import static com.mygdx.game.Game2D.Manager.ResourceManager.pixel10;
import static com.mygdx.game.Game2D.Screens.GameScreen.player;

public class PauseScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private Dialog settingsDialog = new Dialog("Settings", ResourceManager.skin);

    public PauseScreen (Game2D game) {
        super(game);
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("data/uiskin.json")); // You need a skin file

        //Change font of the pausecreen
        skin.getFont("default-font").dispose();
        skin.add("default-font",pixel10);


        Label settingsLabel = new Label("Settings", skin);
        settingsLabel.setColor(Color.BLACK);

        settingsDialog.getContentTable().add(settingsLabel);

        Button resumeButton = createButton("Resume");
        Button settingsButton = createButton("Settings");
        Button saveButton = createButton("Save");
        Button exitButton = createButton("Exit");

        Table table = new Table();
        table.setFillParent(true);
        Table pause = new Table();

        TextureRegionDrawable background = new TextureRegionDrawable(ResourceManager.getInstance().atlas.findRegion("pause_bg"));
        pause.setBackground(background);
        pause.center();
        pause.pad(20f, 20f, 20f, 20f);

        Label pauseLabel = new Label("Game Paused", skin);
        pauseLabel.setColor(Color.BLACK);

        pause.add(pauseLabel).padBottom(20);
        pause.row();
        pause.add(resumeButton).padBottom(20);
        pause.row();
        pause.add(settingsButton).padBottom(20);
        pause.row();
        pause.add(saveButton).padBottom(20);
        pause.row();
        pause.add(exitButton).padBottom(20);
        table.add(pause);

        stage.addActor(table);
        settingsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                settingsDialog.show(stage);
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrayList<TransitionEffect> effects = new ArrayList<>();
                effects.add(new FadeOutTransitionEffect(0.5F));

                AudioManager.getInstance().playMusic("Celeste Original Soundtrack - 02 - First Steps");
                setScreenWithTransition(
                        (BaseScreen)game.getScreen(),
                        new MenuScreen(game),
                        effects
                );

            }
        });
        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> {
                    profileManager.saveProfile(player);
                });
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        stage.act();
        stage.draw();
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
    }

    public void hide() {
        Game2D.inputManager.switchtoPauseScreen();
    }

    public Stage getStage() {
        return stage;
    }
}
