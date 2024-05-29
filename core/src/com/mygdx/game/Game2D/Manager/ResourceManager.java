package com.mygdx.game.Game2D.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
    public TextureRegion[][] button;

    // FONT
    public static BitmapFont pixel10;

    // SETTINGS
    public static Skin skin;

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

    public TextureAtlas UI = new TextureAtlas(Gdx.files.internal("assets/atlas/UI/UI.atlas"));
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

        atlas = assetManager.get("atlas/textures.atlas", TextureAtlas.class);
        cursor = new Pixmap(Gdx.files.internal("tool/cursor.png"));
        button = atlas.findRegion("play_button").split(80, 40);

        pixel10 = new BitmapFont(Gdx.files.internal("fonts/pixel.fnt"), atlas.findRegion("pixel"), false);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
    }

    public ImageButton createImageButton(String buttonName, float scale){
        TextureRegion buttonTextureRegion = ResourceManager.getInstance().UI.findRegion(buttonName);
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
            case 18 -> TextureAtlasNPC.NPC19.getPath();
            case 19 -> TextureAtlasNPC.NPC20.getPath();
            default -> null;
        };
    }

}
