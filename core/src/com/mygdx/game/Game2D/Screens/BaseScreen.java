package com.mygdx.game.Game2D.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game2D.Game2D;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.Screens.transition.effects.TransitionEffect;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Game2D.Manager.ResourceManager.MAINMENU_TEXTURE_ATLAS;


public class BaseScreen implements Screen{
    protected final Game2D game;
    protected ResourceManager resourceManager;
    protected OrthographicCamera gameCam;
    protected OrthographicCamera battleCam;
    // viewport that keeps aspect ratios of the game when resizing
    protected Viewport viewport;
    // main stage of each screen
    protected Stage stage;
//    protected AudioObserver.AudioTypeEvent musicTheme;

//    private Array<AudioObserver> observers;

    public BaseScreen(Game2D game) {
        this.game = game;
        this.resourceManager = Game2D.resourceManager;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Initialize the stage
//        Gdx.input.setInputProcessor(this.stage); //
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
        button.getLabel().setColor(new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1));

        return button;
    }

    public ImageButton createImageButton(String buttonName, Table table) {
        TextureRegion buttonRegion = MAINMENU_TEXTURE_ATLAS.findRegion(buttonName);

        if (buttonRegion == null) {
            Gdx.app.error("createImageButton", "Region not found for button: " + buttonName);
            return null;
        }

//
//        int newWidth = (int) (buttonRegion.getRegionWidth() * 50 );
//        int newHeight = (int) (buttonRegion.getRegionHeight() * 50);
//
//        // Set the new size for the TextureRegion
//        buttonRegion.setRegionWidth(newWidth);
//        buttonRegion.setRegionHeight(newHeight);



        int newWidth = (int) (buttonRegion.getRegionWidth() * 0.1f);
        int newHeight = (int) (buttonRegion.getRegionHeight() * 0.1f);

        TextureRegion scaledButtonRegion = new TextureRegion(buttonRegion);
        scaledButtonRegion.setRegionWidth(newWidth);
        scaledButtonRegion.setRegionHeight(newHeight);

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = new TextureRegionDrawable(buttonRegion); // Set the image for the button
        buttonStyle.imageUp.setMinWidth(200);
        buttonStyle.imageUp.setMinHeight(150);


        ImageButton button = new ImageButton(buttonStyle);



        table.add(button).pad(10); // Adjust padding as needed

        return button;
    }

    public Table createTable() {
        Table table = new Table();
        table.setBounds(0,0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        return table;
    }

    @Override
    public void show() {
        // Nothing
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
        //stage.dispose();
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public OrthographicCamera getBattleCam() {
        return battleCam;
    }

    public Stage getStage() {
        return stage;
    }

}
