package com.mygdx.game;

public class ScreenConfig {
    public static final int originalTileSize = 16;
    public static final int scale = 3;

    public static final int tileSize = originalTileSize * scale;
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;
}
