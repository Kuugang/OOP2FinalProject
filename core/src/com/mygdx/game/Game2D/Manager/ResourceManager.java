package com.mygdx.game.Game2D.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {
    public static ResourceManager instance = null;
    protected boolean isOptionScreen;
    protected boolean isMenuNewGameScreen;
    protected boolean isMenuLoadGameScreen;
    private static InternalFileHandleResolver filePathResolver =  new InternalFileHandleResolver();
//    private final static String ITEMS_TEXTURE_ATLAS_PATH = "skins/items.atlas";

    // MAP
    public final static int SQUARE_TILE_SIZE = 32;

    // ATLAS
    public TextureAtlas atlas;

    // ITEMS
//    public static TextureAtlas ITEMS_TEXTURE_ATLAS = new TextureAtlas(ITEMS_TEXTURE_ATLAS_PATH);

    // STATUS
    private final static String STATUSUI_TEXTURE_ATLAS_PATH = "skins/statusui.atlas";
    private final static String STATUS_UI_SKIN_PATH = "skins/statusui.json";
    public static TextureAtlas STATUS_UI_TEXTURE_ATLAS = new TextureAtlas(STATUSUI_TEXTURE_ATLAS_PATH);
    public static Skin STATUS_UI_SKIN = new Skin(Gdx.files.internal(STATUS_UI_SKIN_PATH), STATUS_UI_TEXTURE_ATLAS);

    // IMAGES
    public Texture backgroundSheet;
    public Texture battleBackgroundMeadow;
    public Pixmap cursor;

    // BUTTON
    public final static String MAINMENU_TEXTURE_ATLAS_PATH = "skins/mainmenu/mainmenu.atlas";
    public static TextureAtlas MAINMENU_TEXTURE_ATLAS = new TextureAtlas(MAINMENU_TEXTURE_ATLAS_PATH);
    public TextureRegion[][] button;

    // FONT
    public static BitmapFont pixel10;

    // SETTINGS
    public static Skin skin;

    // ENTITIES
    public Texture rabite;
    public Texture heroWalkUp;
    public Texture rabiteWalkDown;

    //Sounds

    private static AssetManager assetManager = new AssetManager();


    public TextureAtlas textureAtlas;
    public Animation<TextureRegion> upAnimation;
    public Animation<TextureRegion> downAnimation;
    public Animation<TextureRegion> leftAnimation;
    public Animation<TextureRegion> rightAnimation;
    public Animation<TextureRegion> idleUpAnimation;
    public Animation<TextureRegion> idleDownAnimation;
    public Animation<TextureRegion> idleLeftAnimation;
    public Animation<TextureRegion> idleRightAnimation;


    public ResourceManager() {
        // ATLAS
        assetManager.load("atlas/textures.atlas", TextureAtlas.class);
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




//        // IMAGES
//        assetManager.load("asset/background/natureBackground_frames_sheet.png", Texture.class);
//        assetManager.load("asset/background/battleBackground_meadow.png", Texture.class);
//
//        // ENTITIES
//        assetManager.load("entities/hero/hero_1_walking_up.png", Texture.class);
//
//        assetManager.finishLoading();
//
        atlas = assetManager.get("atlas/textures.atlas", TextureAtlas.class);
        cursor = new Pixmap(Gdx.files.internal("tool/cursor.png"));
//
//        // IMAGES
//        backgroundSheet = assetManager.get("asset/background/natureBackground_frames_sheet.png");
//        battleBackgroundMeadow = assetManager.get("asset/background/battleBackground_meadow.png");
        // BUTTON
        button = atlas.findRegion("play_button").split(80, 40);

//        // FONT
        pixel10 = new BitmapFont(Gdx.files.internal("fonts/pixel.fnt"), atlas.findRegion("pixel"), false);

//        // SETTINGS
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//
//        // ENTITIES
//        heroWalkUp = assetManager.get("entities/hero/hero_1_walking_up.png");
    }

    public static ResourceManager getInstance(){
        if(instance == null){
            instance = new ResourceManager();
        }
        return instance;
    }

    public boolean isOptionScreen() {
        return isOptionScreen;
    }

    public void setOptionScreen(boolean optionScreen) {
        isOptionScreen = optionScreen;
    }

    public boolean isMenuNewGameScreen() {
        return isMenuNewGameScreen;
    }

    public void setMenuNewGameScreen(boolean menuNewGameScreen) {
        isMenuNewGameScreen = menuNewGameScreen;
    }

    public boolean isMenuLoadGameScreen() {
        return isMenuLoadGameScreen;
    }

    public void setMenuLoadGameScreen(boolean menuLoadGameScreen) {
        isMenuLoadGameScreen = menuLoadGameScreen;
    }

    public static void loadMapAsset(String mapFilenamePath) {
        if (mapFilenamePath == null || mapFilenamePath.isEmpty()) {
            return;
        }

        if (assetManager.isLoaded(mapFilenamePath)) {
            return;
        }

        //load asset
        if (filePathResolver.resolve(mapFilenamePath).exists() ) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));
            assetManager.load(mapFilenamePath, TiledMap.class);
            //Until we add loading screen, just block until we load the map
            assetManager.finishLoadingAsset(mapFilenamePath);
        } else {
        }
    }

    public static boolean isAssetLoaded(String fileName) {
        return assetManager.isLoaded(fileName);
    }

    public static TiledMap getMapAsset(String mapFilenamePath) {
        TiledMap map = null;

        // once the asset manager is done loading
        if (assetManager.isLoaded(mapFilenamePath)) {
            map = assetManager.get(mapFilenamePath, TiledMap.class);
        } else {
        }

        return map;
    }

    public static void loadTextureAsset(String textureFilenamePath) {
        if (textureFilenamePath == null || textureFilenamePath.isEmpty()) {
            return;
        }

        if (assetManager.isLoaded(textureFilenamePath)) {
            return;
        }

        //load asset
        if (filePathResolver.resolve(textureFilenamePath).exists()) {
            assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));
            assetManager.load(textureFilenamePath, Texture.class);
            //Until we add loading screen, just block until we load the map
            assetManager.finishLoadingAsset(textureFilenamePath);
        } else {
        }
    }

    public static Texture getTextureAsset(String textureFilenamePath) {
        Texture texture = null;

        // once the asset manager is done loading
        if (assetManager.isLoaded(textureFilenamePath)) {
            texture = assetManager.get(textureFilenamePath,Texture.class);
        } else {
        }

        return texture;
    }

    public static void loadMusicAsset(String musicFilenamePath) {
        if (musicFilenamePath == null || musicFilenamePath.isEmpty()) {
            return;
        }

        if (assetManager.isLoaded(musicFilenamePath)) {
            return;
        }

        //load asset
        if (filePathResolver.resolve(musicFilenamePath).exists()) {
            assetManager.setLoader(Music.class, new MusicLoader(filePathResolver));
            assetManager.load(musicFilenamePath, Music.class);
            //Until we add loading screen, just block until we load the map
            assetManager.finishLoadingAsset(musicFilenamePath);
        } else {
        }
    }

    public static Music getMusicAsset(String musicFilenamePath) {
        Music music = null;

        // once the asset manager is done loading
        if (assetManager.isLoaded(musicFilenamePath)) {
            music = assetManager.get(musicFilenamePath, Music.class);
        } else {
        }

        return music;
    }

    public void dispose() {
        assetManager.dispose();

        atlas.dispose();

        backgroundSheet.dispose();
        battleBackgroundMeadow.dispose();

        pixel10.dispose();
    }
}
