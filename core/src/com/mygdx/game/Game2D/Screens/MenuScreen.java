package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Screens.transition.effects.FadeOutTransitionEffect;
import com.mygdx.game.Game2D.Screens.transition.effects.TransitionEffect;
import com.mygdx.game.Game2D.status.PlayerPortrait;

import java.util.ArrayList;

import static com.mygdx.game.Game2D.Game2D.batch;


public class MenuScreen extends BaseScreen {

    private Table menuTable;
    private Stage menuStage = new Stage();
    private Animation<TextureRegion> flowAnimation;
    private float stateTime;
    PlayerPortrait portrait;

    public MenuScreen(Game2D game) {
        super(game);
//        super.musicTheme = MENU_THEME;

//        portrait = new Portrait();
//        portrait.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center); // Adjust position as needed
//        menuStage.addActor(portrait);
//        Gdx.input.setInputProcessor(menuStage);
menuTable = createTable();
menuTable.setFillParent(true);
menuTable.bottom().padBottom(20);

//        handleBackground();
        handlePlayButton();
        handleMultiplayerButton();
        handleLoadButton();
//        handleOptionButton();
        handleExitButton();
    }

    private void handlePlayButton() {

       Actor newButton =  createImageButton("new_button", 0, 0,  menuTable);
        newButton.setSize(40,40);


//        createButton("Play", 0, menuTable.getHeight()/10, menuTable);
//        Actor newButton = menuTable.getCells().get(0).getActor();


        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                ArrayList<TransitionEffect> effects = new ArrayList<>();
                effects.add(new FadeOutTransitionEffect(1F));
                setScreenWithTransition(
                        (BaseScreen) game.getScreen(),
                        new GameScreen(game),
                        effects
                );
            }
        });
    }

    private void handleMultiplayerButton(){
        Actor multiPlayerButton =  createImageButton("online_button", 0, 0, menuTable);
//        Actor multiPlayerButton = menuTable.getCells().get(1).getActor();

        multiPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                ArrayList<TransitionEffect> effects = new ArrayList<>();
                effects.add(new FadeOutTransitionEffect(1F));
                setScreenWithTransition(
                        (BaseScreen) game.getScreen(),
                        new GameScreen(game),
                        effects
                );
            }
        });
    }

    private void handleExitButton() {
//        createButton("Exit", 0, menuTable.getHeight()/8, menuTable);
        Actor exitButton =  createImageButton("exit_button", 0, 0,  menuTable);
//        Actor exitButton = menuTable.getCells().get(2).getActor();
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                Gdx.app.exit();
            }
        });
    }


    private void handleOptionButton() {
//        createButton("Options", 0, menuTable.getHeight()/10, menuTable);

//        Actor optionButton = menuTable.getCells().get(2).getActor();
//        optionButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent even, float x, float y) {
//                setScreenWithTransition(
//                        (BaseScreen) game.getScreen(),
//                        new OptionScreen(game, (BaseScreen) game.getScreen(), resourceManager),
//                        new ArrayList<>()
//                );
//            }
//        });
    }



    private void handleLoadButton() {
//        createButton("Load Game", 0, menuTable.getHeight()/15, menuTable);
        Actor loadButton = createImageButton("load_button", 0, 0,   menuTable);
            loadButton.setSize(64, 48);
//        Actor loadButton = menuTable.getCells().get(1).getActor();
//        loadButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent even, float x, float y) {
//                setScreenWithTransition((BaseScreen) game.getScreen(), new MenuLoadGameScreen(game, (BaseScreen) game.getScreen(), resourceManager), new ArrayList<>());
//            }
//        });
    }

    @Override
    public void show() {
        menuStage.addActor(menuTable);
        Gdx.input.setInputProcessor(menuStage);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(resourceManager.cursor, 0, 0));

//        notify(AudioObserver.AudioCommand.MUSIC_LOAD, MENU_THEME);
//        notify(AudioObserver.AudioCommand.MUSIC_PLAY_LOOP, MENU_THEME);
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.end();

//        if (!resourceManager.isOptionScreen() && !resourceManager.isMenuNewGameScreen() && !resourceManager.isMenuLoadGameScreen()) {
            menuStage.act(delta);
            menuStage.draw();
//        }
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTable.remove();
    }
}
