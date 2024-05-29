package com.mygdx.game.Game2D.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Game2D.Dialogues.Dialogues;

import java.util.ArrayList;

public class ResourceManager {
    public static ResourceManager instance = null;
    protected boolean isOptionScreen;
    protected boolean isMenuNewGameScreen;
    protected boolean isMenuLoadGameScreen;
    private static InternalFileHandleResolver filePathResolver =  new InternalFileHandleResolver();

    public Pixmap cursor;

    public static BitmapFont pixel10;

    public static Skin skin;

    private static AssetManager assetManager = new AssetManager();

    public Animation<TextureRegion> upAnimation;
    public Animation<TextureRegion> downAnimation;
    public Animation<TextureRegion> leftAnimation;
    public Animation<TextureRegion> rightAnimation;
    public Animation<TextureRegion> idleUpAnimation;
    public Animation<TextureRegion> idleDownAnimation;
    public Animation<TextureRegion> idleLeftAnimation;
    public Animation<TextureRegion> idleRightAnimation;
    public TextureAtlas atlas;
    public ResourceManager() {
        assetManager.load("atlas/UI/UI.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas playerTextureAtlas = new TextureAtlas(Gdx.files.internal("atlas/leo.atlas"));
        upAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("move_up"));
        downAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("move_down"));
        leftAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("move_left"));
        rightAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("move_right"));

        idleUpAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("idle_up"));
        idleDownAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("idle_down"));
        idleLeftAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("idle_left"));
        idleRightAnimation = new Animation<>(0.10f, playerTextureAtlas.findRegions("idle_right"));

        upAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        downAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        leftAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        rightAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        idleUpAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        idleDownAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        idleLeftAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        idleRightAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        atlas = assetManager.get("atlas/UI/UI.atlas", TextureAtlas.class);
        cursor = new Pixmap(Gdx.files.internal("tool/cursor.png"));

        pixel10 = new BitmapFont(Gdx.files.internal("fonts/pixel.fnt"));

        skin = new Skin(Gdx.files.internal("skin/skin.json"));
    }

    public ImageButton createImageButton(String buttonName, float scale){
        TextureRegion buttonTextureRegion = ResourceManager.getInstance().atlas.findRegion(buttonName);
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = new TextureRegionDrawable(buttonTextureRegion);

        ImageButton button = new ImageButton(buttonStyle);
        button.setTransform(true);
        button.setScale(scale);

        button.setOrigin(button.getWidth() / 2, button.getHeight() / 2);
        return button;
    }

    public static ResourceManager getInstance(){
        if(instance == null){
            instance = new ResourceManager();
        }
        return instance;
    }

    public void setMenuNewGameScreen(boolean menuNewGameScreen) {
        isMenuNewGameScreen = menuNewGameScreen;
    }

    public void setMenuLoadGameScreen(boolean menuLoadGameScreen) {
        isMenuLoadGameScreen = menuLoadGameScreen;
    }

    public void dispose() {
        assetManager.dispose();

        atlas.dispose();
        pixel10.dispose();
    }

    public static ArrayList<String> getRandomDialogues(){
        return switch (Math.abs(new RandomXS128().nextInt() % 2)){
            case 0 -> Dialogues.dialogues1;
            case 1 -> Dialogues.dialogues2;
            default -> null;
        };
    }

    public static String getRandomTA_NPC(){
        RandomXS128 randomXS128 = new RandomXS128();
        return switch (Math.abs(randomXS128.nextInt() % TextureAtlasNPC.values().length)) {
            case 0 -> TextureAtlasNPC.NPC1.getPath();
            case 1 -> TextureAtlasNPC.NPC2.getPath();
            case 2 -> TextureAtlasNPC.NPC3.getPath();
            case 3 -> TextureAtlasNPC.NPC4.getPath();
            case 4 -> TextureAtlasNPC.NPC5.getPath();
            case 5 -> TextureAtlasNPC.NPC6.getPath();
            case 6 -> TextureAtlasNPC.NPC7.getPath();
            case 7 -> TextureAtlasNPC.NPC8.getPath();
            case 8 -> TextureAtlasNPC.NPC9.getPath();
            case 9 -> TextureAtlasNPC.NPC10.getPath();
            case 10 -> TextureAtlasNPC.NPC11.getPath();
            case 11 -> TextureAtlasNPC.NPC12.getPath();
            case 12 -> TextureAtlasNPC.NPC13.getPath();
            case 13 -> TextureAtlasNPC.NPC14.getPath();
            case 14 -> TextureAtlasNPC.NPC15.getPath();
            case 15 -> TextureAtlasNPC.NPC16.getPath();
            case 16 -> TextureAtlasNPC.NPC17.getPath();
            case 17 -> TextureAtlasNPC.NPC18.getPath();
            case 18, 19 -> TextureAtlasNPC.NPC20.getPath();
            default -> null;
        };
    }
}
