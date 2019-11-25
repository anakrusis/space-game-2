package util;

public class Reference {
    public static final int CHUNK_SIZE = 2048;
    public static final int RENDER_DISTANCE = 1;
    public static final double MIN_ZOOM = 0.001;

    // The point at which normal game rendering stops and the map rendering begins
    // this means culling particles and small entities, and just rendering stars and icons and stuff
    public static final double MAP_SCREEN_THRESHOLD = 0.01;

    public static final String GAME_NAME = "Space Game";
    public static final String VERSION = "0.0.3pre";
}
