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


    private Table menuNewGameTable;
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

        menuNewGameTable = createTable();
        menuNewGameTable.pad(10);
        menuNewGameTable = createTable();
        menuNewGameTable.setFillParent(true);
        menuNewGameTable.add(profileName).center();
        menuNewGameTable.add(profileText).center();

        menuNewGameTable.row();

        createOverwriteDialog();
        menuNewGameTable.add(handlePlayButton()).padRight(10).padTop(10);
        menuNewGameTable.add(handleNewBackButton()).padRight(10);
        handleOverwriteButton();
        handleCancelButton();


//        bottomTable = createTable();
//        bottomTable.setWidth(Gdx.graphics.getWidth());
//        bottomTable.setHeight(Gdx.graphics.getHeight()/2f);
//        bottomTable.center();



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

    private Actor handlePlayButton() {
        Actor playButton = createButton("Play");
        World.username = profileText.getText();
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                previousScreen.dispose();
                setScreenWithTransition((BaseScreen)game.getScreen(), new GameScreen(game), new ArrayList<>());
            }
        });

        return playButton;
    }

    private Actor handleNewBackButton() {
        Actor backButton = createButton("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                setScreenWithTransition((BaseScreen) game.getScreen(), previousScreen, new ArrayList<>());
            }
        });

        return backButton;
    }

    private Actor handleOverwriteButton() {
        Actor overwriteButton = createButton("Overwrite");
        World.username = profileText.getText();
        overwriteButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setGameScreen(new GameScreen(game));
            }
        });


        return overwriteButton;
    }

    private Actor handleCancelButton() {
        Actor cancelButton = createButton("Cancel");
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                overwriteDialog.hide();
            }
        });
        return cancelButton;
    }

    @Override
    public void show() {
        newStage.addActor(menuNewGameTable);
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
    }

    @Override
    public void hide() {
        resourceManager.setMenuNewGameScreen(false);
    }
}
