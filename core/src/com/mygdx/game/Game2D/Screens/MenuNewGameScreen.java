package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Screens.transition.effects.FadeOutTransitionEffect;
import com.mygdx.game.Game2D.Screens.transition.effects.TransitionEffect;
import com.mygdx.game.Game2D.World.World;

import java.util.ArrayList;

public class MenuNewGameScreen extends BaseScreen {


    private Table newTable;
    private Table topTable;
    private Table bottomTable;
    private Stage newStage = new Stage();
    private TextField profileText;
    private Dialog overwriteDialog;
    private BaseScreen previousScreen;
    private float stateTime;
    private Entity player;

    public MenuNewGameScreen(Game2D game, BaseScreen previousScreen, ResourceManager resourceManager) {
        super(game);
        this.previousScreen = previousScreen;

        resourceManager.setMenuNewGameScreen(true);

        Label profileName = new Label("Enter Profile Name: ", ResourceManager.skin);
        profileText = new TextField("Leo", ResourceManager.skin);
        profileText.setMaxLength(20);

        newTable = createTable();

        topTable = createTable();
        topTable.setFillParent(true);
        topTable.add(profileName).center();
        topTable.add(profileText).center();

        bottomTable = createTable();
        bottomTable.setWidth(Gdx.graphics.getWidth());
        bottomTable.setHeight(Gdx.graphics.getHeight()/2f);
        bottomTable.center();

        createOverwriteDialog();
        handlePlayButton();
        handleNewBackButton();
        handleOverwriteButton();
        handleCancelButton();

    }

    private void createOverwriteDialog() {
        overwriteDialog = new Dialog("Overwrite?", ResourceManager.skin);
        Label overwriteLabel = new Label("Overwrite existing profile name?", ResourceManager.skin);

        overwriteDialog.setKeepWithinStage(true);
        overwriteDialog.setModal(true);
        overwriteDialog.setMovable(false);
        overwriteDialog.text(overwriteLabel);
        overwriteDialog.row();
    }

    private void handlePlayButton() {
        createButton("Play", 0, newTable.getHeight()/9, newTable);
        World.username = profileText.getText();
        Actor playButton = newTable.getCells().get(0).getActor();
        bottomTable.add(playButton).padRight(50);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                previousScreen.dispose();
                setScreenWithTransition((BaseScreen)game.getScreen(), new GameScreen(game), new ArrayList<>());
            }
        });
    }

    private void handleNewBackButton() {
        createButton("Back",0, newTable.getHeight()/5, newTable);

        Actor backButton = newTable.getCells().get(1).getActor();
        bottomTable.add(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                setScreenWithTransition((BaseScreen) game.getScreen(), previousScreen, new ArrayList<>());
            }
        });
    }

    private void handleOverwriteButton() {
        createButton("Overwrite",0, newTable.getHeight()/5, newTable);

        Actor overwriteButton = newTable.getCells().get(2).getActor();
        overwriteDialog.button((Button) overwriteButton).bottom().left();
        overwriteButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setGameScreen(new GameScreen(game));
            }
        });
    }

    private void handleCancelButton() {
        createButton("Cancel",0, newTable.getHeight()/5, newTable);

        Actor cancelButton = newTable.getCells().get(3).getActor();
        overwriteDialog.button((Button) cancelButton).bottom().right();
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                overwriteDialog.hide();
            }
        });
    }

    @Override
    public void show() {
        newStage.addActor(newTable);
        newStage.addActor(topTable);
        newStage.addActor(bottomTable);
        Gdx.input.setInputProcessor(newStage);
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();

        if (previousScreen != null) {
            previousScreen.render(stateTime);
        }

        show();
        newStage.act(delta);
        newStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        newTable.remove();
    }

    @Override
    public void hide() {
        resourceManager.setMenuNewGameScreen(false);
    }
}
