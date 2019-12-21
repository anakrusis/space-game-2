package com.adnre.spacegame.util;

public class Reference {
    public static final int CHUNK_SIZE = 4096;
    public static final int RENDER_DISTANCE = 1;
    public static final double MIN_ZOOM = 0.001;
    public static final double MAX_ZOOM = 4;

    // Furthest away a player can click to use a tool
    public static final double TOOL_USE_RADIUS = 16;

    // The point at which normal game rendering stops and the map rendering begins
    // this means culling particles and small entities, and just rendering stars and icons and stuff
    public static final double MAP_SCREEN_THRESHOLD = 0.005;

    public static final String GAME_NAME = "Space Game";
    public static final String VERSION = "0.0.5pre";

    public static final boolean DEBUG_MODE = true;
    // DeaSTL wanted me to make it hot pink so if you want your build to have a hot pink background, you know what to do
    public static final boolean DEASTL_MODE = false;
}
